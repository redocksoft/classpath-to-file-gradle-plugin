package com.redock.classpathtofile.listener

import com.redock.classpathtofile.plugin.ClasspathToFilePluginExtension
import org.gradle.api.Task
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.process.JavaExecSpec

class JavaExecSpecActionListener(private val extension: ClasspathToFilePluginExtension): AbstractClasspathToFileActionListener() {
  override val log: Logger = Logging.getLogger(JavaExecSpecActionListener::class.java)

  override fun beforeActions(task: Task) {
    if(task !is JavaExecSpec || !extension.shouldApply()) {
      return
    }

    classpathToFile(task, task.jvmArgumentProviders, task.classpath, task.mainModule.isPresent)
    task.classpath = EmptyFileCollection
  }
}
