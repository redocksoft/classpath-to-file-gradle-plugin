package com.redock.classpathtofile.listener

import org.gradle.api.Task
import org.gradle.api.execution.TaskActionListener
import org.gradle.api.file.FileCollection
import org.gradle.api.logging.Logger
import org.gradle.process.CommandLineArgumentProvider
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

/**
 * Unfortunately JavaExecSpec and Test don't share a common interface, though they both provide
 * `jvmArgumentProviders` and `classpath`. Use this abstract class to encapsulate the shared logic.
 */
abstract class AbstractClasspathToFileActionListener: TaskActionListener {
  protected abstract val log: Logger

  private lateinit var cpArgFile: File

  fun classpathToFile(
    task: Task,
    jvmArgumentProviders: MutableList<CommandLineArgumentProvider>,
    classpath: FileCollection
  ) {

    cpArgFile = File.createTempFile("classpath-${task.project.name.replace(" ", "_")}", null)

    log.info("Moving java classpath to argument file for task ${task.name}")
    jvmArgumentProviders.add(CommandLineArgumentProvider {
      listOf("@$cpArgFile")
    })

    // TODO this doesn't currently handle wildcards, we should expand wildcards before writing to the arg file
    // see https://docs.oracle.com/javase/9/tools/java.htm#JSWOR-GUID-4856361B-8BFD-4964-AE84-121F5F6CF111
    OutputStreamWriter(FileOutputStream(cpArgFile), Charsets.UTF_8).use {
      it.write("-cp \"\\")
      it.write("\n")
      classpath.files.forEachIndexed { i, file  ->
        if(i > 0) {
          it.write(":\\\n")
        }

        it.write(file.canonicalPath)
      }
      it.write("\"")
    }
  }

  override fun afterActions(task: Task) {
    if(::cpArgFile.isInitialized) {
      cpArgFile.delete()
    }
  }
}
