import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class DataUtil {
    fun saveTestResult(name: String, values: List<Int>) {
        val path = Paths.get("", "data")
        println("Writing to: %´${path.toUri()}")
        Files.createDirectories(path)
        val fos = Files.newBufferedWriter(path.resolve("data_${name}.txt"), StandardOpenOption.CREATE, StandardOpenOption.APPEND)
        values.forEach {
            fos.write("" + it + "\n")
        }
        fos.write("\n")
        fos.flush()
        fos.close()
    }

    fun saveTestResult(name: String, value: Int) {

    }
}
