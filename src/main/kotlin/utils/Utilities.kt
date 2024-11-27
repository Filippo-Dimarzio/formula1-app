package ie.setu.utils


import models.Driver

fun formatListString(formulas1ToFormat: List<Driver>): String =
    formulas1ToFormat
        .joinToString(separator = "\n") { formula1 -> "$formula1" }

fun formatSetString(formulas1ToFormat: Set<Driver>): String =
    formulas1ToFormat
        .joinToString(separator = "\n") { item -> "\t$item" }
