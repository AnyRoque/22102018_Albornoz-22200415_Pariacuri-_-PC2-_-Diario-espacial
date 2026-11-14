package com.esan.diarioespacial.data.local

import androidx.room.Dao
import androidx.room.Query

// Base común: el Alumno 1 lo completa.
@Dao
interface ApodDao {
    @Query("SELECT COUNT(*) FROM apod")
    suspend fun count(): Int
}