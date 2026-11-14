package com.esan.diarioespacial.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// Base común: el Alumno 2 lo completa.
@Entity(tableName = "asteroids")
data class AsteroidEntity(
    @PrimaryKey val id: String
)