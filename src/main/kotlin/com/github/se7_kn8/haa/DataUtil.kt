package com.github.se7_kn8.haa

import java.io.BufferedWriter
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class DataUtil(private val userName: String) {

    val userPath = Paths.get("", "data", "user")
    val path = Paths.get("", "data")

    var userFos: BufferedWriter

    init {
        Files.createDirectories(path)
        Files.createDirectories(userPath)
        println("Writing user data to: %´${userPath.toUri()}")
        userFos = Files.newBufferedWriter(
            userPath.resolve("data_${userName}.txt"),
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND
        )
        userFos.write("#User profile for: ${userName}\n")
        userFos.flush()
    }

    fun saveTestResult(name: String, values: List<Int>) {
        println("Writing to: %´${path.toUri()}")
        userFos.write("#$name\n")
        val fos = Files.newBufferedWriter(
            path.resolve("data_${name}.txt"),
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND
        )
        values.forEach {
            userFos.write("" + it + "\n")
            fos.write("" + it + "\n")
        }
        userFos.write("\n")
        fos.write("\n")
        userFos.flush()
        fos.flush()
        fos.close()
    }

    fun close() {
        userFos.flush()
        userFos.close()
    }

}
