package com.esan.diarioespacial.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// Base común: el Alumno 1 lo completa.
@Entity(tableName = "apod")
data class ApodEntity(
    @PrimaryKey val date: String
)