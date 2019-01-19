package com.redock.classpathtofile.plugin

import com.redock.classpathtofile.listener.JavaExecSpecActionListener
import com.redock.classpathtofile.listener.TestActionListener
import org.gradle.api.Plugin
import org.gradle.api.Project

class ClasspathToFilePlugin: Plugin<Project> {
  override fun apply(project: Project) {
    // this method isn't even available on JDKs less than 9
    try {
      if(Runtime.version().major() < 9) jvmRequirementError()
    } catch (e: NoSuchMethodError) {
      jvmRequirementError()
    }

    val ext = project.extensions.create("classpathToFile", ClasspathToFilePluginExtension::class.java, project)
    project.gradle.addListener(JavaExecSpecActionListener(ext))
    project.gradle.addListener(TestActionListener(ext))
  }

  private fun jvmRequirementError() {
    error("Only JVM versions 9+ supports the necessary file-based classpath mechanism this plugin requires")
  }
}
