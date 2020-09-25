import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.9"
    id("org.beryx.jlink") version "2.22.0"
}

group = "com.github.se7_kn8"
version = "1.0"

repositories {
    mavenCentral()
}

// TODO include javafx runtime dependencies to make this cross platform
dependencies {
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

val compileKotlin: KotlinCompile by tasks
val compileJava: JavaCompile by tasks
compileJava.destinationDir = compileKotlin.destinationDir

javafx {
    version = "15"
    modules = listOf("javafx.base", "javafx.graphics")
}

application {
    mainClassName = "haa/com.github.se7_kn8.haa.App"
}

jlink {
    launcher {
        name = "HumanAbilityAnalytics"
    }
}
