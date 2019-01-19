import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.3.11"
  id("java-gradle-plugin")
  id("com.gradle.plugin-publish").version("0.10.0")
}

group = "com.redock.classpathtofile"
version = "0.0.1"

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
  jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
  jvmTarget = "1.8"
}

repositories {
  mavenCentral()
}

dependencies {
  compileOnly(gradleApi())
  implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.11")

  testCompileOnly(gradleTestKit())
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
  testImplementation("org.junit.jupiter:junit-jupiter-engine:5.3.1")
}

pluginBundle {
  website = "https://github.com/redocksoft/classpath-to-file-gradle-plugin"
  vcsUrl = "https://github.com/redocksoft/classpath-to-file-gradle-plugin.git"
  tags = listOf("classpath-issue", "windows-gradle-long-classpath", "javaexec-task", "createprocess error=206")
}

gradlePlugin {
  plugins {
    create("classpathToFilePlugin") {
      id = "com.redock.classpathtofile"
      displayName = "com.redock.classpathtofile"
      description = "Fix for Windows long classpath issue. For JDK9+, fixes JavaExec/Test tasks that error with message: \"CreateProcess error=206, The filename or extension is too long\""
      implementationClass = "com.redock.classpathtofile.plugin.ClasspathToFilePlugin"
    }
  }
}

tasks.wrapper {
  distributionType = Wrapper.DistributionType.ALL
}
