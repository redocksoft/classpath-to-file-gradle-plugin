package com.redock.classpathtofile.listener

import com.redock.classpathtofile.plugin.ClasspathToFilePluginExtension
import org.gradle.api.Task
import org.gradle.api.execution.TaskActionListener
import org.gradle.api.internal.file.AbstractFileCollection
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.TaskDependency
import org.gradle.process.CommandLineArgumentProvider
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class JavaExecActionListener(private val extension: ClasspathToFilePluginExtension): TaskActionListener {
  companion object {
    private val log: Logger = Logging.getLogger(JavaExecActionListener::class.java)

    object EmptyFileCollection: AbstractFileCollection() {
      override fun getFiles(): MutableSet<File> = mutableSetOf()
      override fun getBuildDependencies(): TaskDependency? = null
      override fun getDisplayName(): String = "EmptyFileCollection"
    }
  }

  private lateinit var cpArgFile: File

  override fun beforeActions(task: Task) {
    if(task !is JavaExec || !extension.shouldApply()) {
      return
    }

    cpArgFile = File.createTempFile("classpath-${task.project.name.replace(" ", "_")}", null)

    log.info("Moving java classpath to argument file for task ${task.name}")
    task.jvmArgumentProviders.add(CommandLineArgumentProvider {
      listOf("@$cpArgFile")
    })

    // TODO this doesn't currently handle wildcards, we should expand wildcards before writing to the arg file
    // see https://docs.oracle.com/javase/9/tools/java.htm#JSWOR-GUID-4856361B-8BFD-4964-AE84-121F5F6CF111
    OutputStreamWriter(FileOutputStream(cpArgFile), Charsets.UTF_8).use {
      it.write("-cp \"\\")
      it.write("\n")
      task.classpath.files.forEachIndexed { i, file  ->
        if(i > 0) {
          it.write(":\\\n")
        }

        it.write(file.canonicalPath)
      }
      it.write("\"")
    }

    task.classpath = EmptyFileCollection
  }

  override fun afterActions(task: Task) {
    if(::cpArgFile.isInitialized) {
      cpArgFile.delete()
    }
  }
}
