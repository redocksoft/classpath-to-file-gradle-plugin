import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.3.11"
  id("java-gradle-plugin")
  id("maven-publish")
  id("com.gradle.plugin-publish").version("0.9.10")
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

gradlePlugin {
  plugins {
    create("classpathToFilePlugin") {
      id = "com.redock.classpathtofile"
      implementationClass = "com.redock.classpathtofile.plugin.ClasspathToFilePlugin"
    }
  }
}

tasks.wrapper {
  distributionType = Wrapper.DistributionType.ALL
}
