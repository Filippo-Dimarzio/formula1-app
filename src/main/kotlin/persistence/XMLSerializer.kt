package persistence


import com.thoughtworks.xstream.io.xml.DomDriver
import ie.setu.models.Formula1
import ie.setu.persistence.Serializer
import java.io.File
import java.io.FileReader
import java.io.FileWriter


abstract class XMLSerializer(private val file: File) : Serializer {


    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Formula1::class.java))
        FileReader(file).use { inputStream ->
            return xStream.fromXML(inputStream)
        }
    }


    @Throws(Exception::class)
    override fun write(formulas1: ArrayList<Formula1>) {
        val xStream = XStream(DomDriver()) // Use DomDriver for XML serialization
        FileWriter(file).use { outputStream ->
            xStream.toXML(formulas1, outputStream) // Serialize ArrayList<Formula1> to XML file
        }
    }


    fun filter(obj: Any): Any {
        return obj // Modify or filter the object if needed before serialization
    }
}

@Throws(Exception::class)
fun load(serializer: XMLSerializer): ArrayList<Formula1> {
    return serializer.read() as ArrayList<Formula1> // Load and cast to ArrayList<Formula1>
}


@Throws(Exception::class)
fun store(serializer: XMLSerializer, drivers: ArrayList<Formula1>) {
    serializer.write(drivers) // Serialize the ArrayList<Formula1> objects to XML file
}
