package com.redock.classpathtofile.listener

import com.redock.classpathtofile.plugin.ClasspathToFilePluginExtension
import org.gradle.api.Task
import org.gradle.api.execution.TaskActionListener
import org.gradle.api.logging.Logger
import org.gradle.api.logging.Logging
import org.gradle.api.tasks.testing.Test

class TestActionListener(private val extension: ClasspathToFilePluginExtension): AbstractClasspathToFileActionListener(), TaskActionListener {
  override val log: Logger = Logging.getLogger(TestActionListener::class.java)

  override fun beforeActions(task: Task) {
    if(task !is Test || !extension.shouldApply()) {
      return
    }

    classpathToFile(task, task.jvmArgumentProviders, task.classpath, false)
    task.classpath = EmptyFileCollection
  }
}
