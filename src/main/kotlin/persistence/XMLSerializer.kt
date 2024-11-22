package persistence

import ie.setu.models.Formula1
import ie.setu.persistence.Serializer
import java.io.File
import java.io.FileReader
import java.io.FileWriter

abstract class XMLSerializer(private val file: File) : Serializer {

    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver())  // Create an instance of XStream using DomDriver
        xStream.allowTypes(arrayOf(Formula1::class.java))  // Allow Formula1 type for deserialization
        FileReader(file).use { inputStream ->
            return xStream.fromXML(inputStream) // Deserialize from XML file to objects
        }
    }

    private fun DomDriver(): Any {

    }

    @Throws(Exception::class)
    override fun write(formulas1: ArrayList<Formula1>) {
        val xStream = XStream(DomDriver())  // Create an instance of XStream using DomDriver
        FileWriter(file).use { outputStream ->
            xStream.toXML(formulas1, outputStream)  // Serialize ArrayList<Formula1> to XML file
        }
    }

    // Optional filter function for data processing before serialization
    fun filter(obj: Any): Any {

        return obj
    }
}

// Functions to load and store Formula1 objects

@Throws(Exception::class)
fun load(serializer: XMLSerializer): ArrayList<Formula1> {
    return serializer.read() as ArrayList<Formula1> // Load as ArrayList<Formula1>
}

@Throws(Exception::class)
fun store(serializer: XMLSerializer, drivers: ArrayList<Formula1>) {
    serializer.write(drivers) // Store ArrayList<Formula1> objects to XML
}
