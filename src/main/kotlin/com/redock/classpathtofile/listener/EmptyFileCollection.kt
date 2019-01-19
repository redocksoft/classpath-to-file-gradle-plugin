package com.redock.classpathtofile.listener

import org.gradle.api.internal.file.AbstractFileCollection
import org.gradle.api.tasks.TaskDependency
import java.io.File

object EmptyFileCollection: AbstractFileCollection() {
  override fun getFiles(): MutableSet<File> = mutableSetOf()
  override fun getBuildDependencies(): TaskDependency? = null
  override fun getDisplayName(): String = "EmptyFileCollection"
}
