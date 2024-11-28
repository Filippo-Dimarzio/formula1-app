package ie.setu.persistence

import Team


interface Serializer {
        @Throws(Exception::class)
        fun write(obj: Any?)

        @Throws(Exception::class)
        fun read(): Any?
        fun save(teams: MutableList<Team>, fileName: String) {

        }
    }
