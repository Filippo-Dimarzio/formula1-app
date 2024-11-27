package persistence


import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import ie.setu.persistence.Serializer
import models.Driver
import java.io.File
import java.io.FileReader
import java.io.FileWriter


class XMLSerializer(private val file: File) : Serializer {


    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Driver::class.java))

        FileReader(file).use { inputStream ->

            return xStream.fromXML(inputStream)
        }
    }


    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(DomDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }




}

