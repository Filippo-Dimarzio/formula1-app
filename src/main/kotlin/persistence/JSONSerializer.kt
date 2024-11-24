package persistence

import Team
import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver
import ie.setu.models.Formula1
import ie.setu.persistence.Serializer
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class JSONSerializer(private val file: File) : Serializer {

    // Setup XStream with JSON driver and allow specific types for deserialization
    override fun XStream(domDriver: Any): XStream {
        val xStream = XStream(JettisonMappedXmlDriver())
        xStream.allowTypes(arrayOf(Formula1::class.java))
        return xStream
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

    // Write a list of Formula1 objects to the file in JSON format
    @Throws(IOException::class)
    override fun write(formula1List: ArrayList<Formula1>) {
        val xStream = XStream(JettisonMappedXmlDriver())

        // Create a FileWriter to write the object into the file
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        xStream.toXML(formula1List, outputStream)
        outputStream.close()
    }

    override fun write(team: ArrayList<Team>) {

    }

    // Alternative write method that accepts any object (not specifically ArrayList<Formula1>)
    @Throws(IOException::class)
     fun write(obj: Any?) {
        val xStream = XStream(JettisonMappedXmlDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        xStream.toXML(obj, outputStream)
        outputStream.close()
    }
}
