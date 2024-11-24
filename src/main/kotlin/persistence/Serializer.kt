package ie.setu.persistence

import Team
import ie.setu.models.Formula1

interface Serializer {
     fun XStream(domDriver: Any): Any
     fun read(): Any
    fun write(formulas1: ArrayList<Formula1>)
    fun write(team: ArrayList<Team>)


    interface Serializer {
        @Throws(Exception::class)
        fun write(obj: Any?)

        @Throws(Exception::class)
        fun read(): Any?
    }
}