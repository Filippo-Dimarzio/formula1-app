package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver
import ie.setu.models.Formula1
import ie.setu.persistence.Serializer
import java.io.File
import java.io.FileReader
import java.io.IOException

class JSONSerializer(private val file: File) : Serializer {

    // Setup XStream with JSON driver and allow specific types for deserialization
    override fun write(obj: Any?) {
        val xStream = XStream(JettisonMappedXmlDriver())
        xStream.allowTypes(arrayOf(Formula1::class.java))

    }

    // Read data from the file and deserialize it into an object
    @Throws(IOException::class, ClassNotFoundException::class)
    override fun read(): Any {
        val xStream = XStream(JettisonMappedXmlDriver())
        xStream.allowTypes(arrayOf(Formula1::class.java))

        // Create a FileReader to read the file and then deserialize the object
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as ArrayList<Formula1>
        inputStream.close()
        return obj
    }





}
