import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.8"
}

group = "com.github.se7_kn8"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// TODO include javafx runtime dependencies to make this cross platform
dependencies {
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

javafx {
    version = "15"
    modules = listOf("javafx.base", "javafx.graphics")
}

application {
    mainClassName = "App"
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "AppKt"
    }
    from(
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    )
}
