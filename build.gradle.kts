import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "2.0.0"
  id("java-gradle-plugin")
  id("maven-publish")
  id("com.gradle.plugin-publish").version("1.2.1")
}

group = "com.redock.classpathtofile"
version = "0.1.0"

kotlin {
  compilerOptions {
    apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_0)
  }
}

java {
  sourceCompatibility = JavaVersion.VERSION_1_8
  targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
  compilerOptions {
    jvmTarget.set(JvmTarget.JVM_1_8)
  }
}

repositories {
  mavenCentral()
}

dependencies {
  compileOnly(gradleApi())
  implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.0")

  testCompileOnly(gradleTestKit())
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
  testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.2")
}

gradlePlugin {
  website = "https://github.com/redocksoft/classpath-to-file-gradle-plugin"
  vcsUrl = "https://github.com/redocksoft/classpath-to-file-gradle-plugin.git"
  plugins {
    create("classpathToFilePlugin") {
      id = "com.redock.classpathtofile"
      displayName = "com.redock.classpathtofile"
      description = "Fix for Windows long classpath issue. For JDK9+, fixes JavaExec/Test tasks that error with message: \"CreateProcess error=206, The filename or extension is too long\""
      implementationClass = "com.redock.classpathtofile.plugin.ClasspathToFilePlugin"
      tags = listOf("classpath-issue", "windows-gradle-long-classpath", "javaexec-task", "createprocess error=206")
    }
  }
}

tasks.wrapper {
  distributionType = Wrapper.DistributionType.ALL
}
