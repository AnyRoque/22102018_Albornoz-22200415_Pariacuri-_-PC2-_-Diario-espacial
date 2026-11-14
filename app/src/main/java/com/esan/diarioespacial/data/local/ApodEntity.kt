package com.esan.diarioespacial.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

<<<<<<< HEAD
// Base común: el Alumno 1 lo completa.
@Entity(tableName = "apod")
data class ApodEntity(
    @PrimaryKey val date: String
)
=======
@Entity(tableName = "apod")
data class ApodEntity(
    @PrimaryKey val date: String,          // AAAA-MM-DD, una imagen por día
    val title: String,
    val explanation: String,
    val url: String?,
    val hdUrl: String?,
    val mediaType: String,                 // "image" | "video"
    val copyright: String?,
    val isFavorite: Boolean = false
)
>>>>>>> dc043c597b94fec6cf778a705058c87309583524
