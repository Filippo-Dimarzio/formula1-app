package persistence

import ie.setu.models.Formula1
import ie.setu.persistence.Serializer
import java.io.File
import java.io.FileReader
import java.io.FileWriter

abstract class XMLSerializer(private val file: File) : Serializer {

    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Formula1::class.java)) // Allow Formula1 type
        FileReader(file).use { inputStream ->
            return xStream.fromXML(inputStream) // Use fromXML for reading objects
        }
    }

    private fun DomDriver(): Any {

    }

    @Throws(Exception::class)
    override fun write(formulas1: ArrayList<Formula1>) {
        val xStream = XStream(DomDriver())
        FileWriter(file).use { outputStream ->
            xStream.toXML(obj, outputStream) // Use toXML for writing objects
        }
    }

    // Placeholder filter function to process objects
    fun filter(obj: Any): Any {
        // Implement filtering logic as needed
        // For now, just return the object as is
        return obj
    }
}



// Functions to load and store Formula1 objects

@Throws(Exception::class)
fun load(serializer: XMLSerializer) {
    var formulas1 = serializer.read() as ArrayList<Formula1> // Load as ArrayList<Formula1>
}

@Throws(Exception::class)
fun store(serializer: XMLSerializer, drivers: ArrayList<Formula1>) {
    serializer.write(drivers) // Store ArrayList<Formula1> objects
}
