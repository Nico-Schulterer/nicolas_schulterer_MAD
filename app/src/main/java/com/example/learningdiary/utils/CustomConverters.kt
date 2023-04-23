package com.example.learningdiary.utils

import androidx.room.TypeConverter
import com.example.learningdiary.models.Genre

class CustomConverters {

    @TypeConverter
    fun stringListToString(value: List<String>) = value.joinToString { "," }

    @TypeConverter
    fun stringToStringList(value: String) = value.split(",").map { it.trim() }

    @TypeConverter
    fun enumListToString(value: List<Genre>) = value.joinToString ("," ) { it.name }

    @TypeConverter
    fun stringToEnumList(value: String) = value.split(",").map {Genre.valueOf(it.trim())}
}