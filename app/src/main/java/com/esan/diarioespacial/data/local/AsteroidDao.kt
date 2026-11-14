package com.esan.diarioespacial.data.local

import androidx.room.Dao
import androidx.room.Query

// Base común: el Alumno 2 lo completa.
@Dao
interface AsteroidDao {
    @Query("SELECT COUNT(*) FROM asteroids")
    suspend fun count(): Int
}