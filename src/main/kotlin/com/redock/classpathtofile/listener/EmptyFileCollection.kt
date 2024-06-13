package com.redock.classpathtofile.listener

import org.gradle.api.internal.file.AbstractFileCollection
import org.gradle.api.internal.tasks.TaskDependencyResolveContext
import org.gradle.api.tasks.TaskDependency
import java.io.File

object EmptyFileCollection: AbstractFileCollection() {
  override fun getFiles(): MutableSet<File> = mutableSetOf()
  override fun visitDependencies(context: TaskDependencyResolveContext) {}
  override fun getDisplayName(): String = "EmptyFileCollection"
}
