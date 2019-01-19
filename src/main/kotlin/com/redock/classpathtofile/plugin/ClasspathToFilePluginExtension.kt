package com.redock.classpathtofile.plugin

import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.internal.os.OperatingSystem

open class ClasspathToFilePluginExtension(project: Project) {
  val enableAlways: Property<Boolean> = project.objects.property(Boolean::class.javaObjectType).apply { set(false) }

  fun shouldApply() = this.enableAlways.get() || OperatingSystem.current().isWindows
}
