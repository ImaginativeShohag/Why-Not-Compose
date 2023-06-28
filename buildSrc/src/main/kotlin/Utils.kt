import java.io.File

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
