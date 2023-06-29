import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Get local properties.
 */
fun getLocalProperty(key: String, file: String = "local.properties"): String {
    val properties = java.util.Properties()
    val localProperties = File(file)
    if (localProperties.isFile) {
        java.io.InputStreamReader(java.io.FileInputStream(localProperties), Charsets.UTF_8)
            .use { reader ->
                properties.load(reader)
            }
    } else {
        error("File from not found")
    }

    return properties.getProperty(key)
}

fun getCurrentDateAsYYMMDD(): String {
    val formatter = DateTimeFormatter.ofPattern("yyMMdd")
    return LocalDateTime.now().format(formatter)
}
