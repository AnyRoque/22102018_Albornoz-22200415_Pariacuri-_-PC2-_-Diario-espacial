package com.esan.diarioespacial.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {

    @Query(
        "SELECT * FROM asteroids WHERE approachDate BETWEEN :start AND :end " +
            "ORDER BY approachDate DESC, approachEpoch DESC"
    )
    fun observeRange(start: String, end: String): Flow<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroids WHERE isFavorite = 1 ORDER BY approachDate DESC, approachEpoch DESC")
    fun observeFavorites(): Flow<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroids WHERE id = :id")
    fun observeById(id: String): Flow<AsteroidEntity?>

    @Query("SELECT id FROM asteroids WHERE isFavorite = 1")
    suspend fun favoriteIds(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<AsteroidEntity>)

    @Query("UPDATE asteroids SET isFavorite = :favorite WHERE id = :id")
    suspend fun setFavorite(id: String, favorite: Boolean)

    /** Guarda lo que llegó de la API SIN perder los favoritos ya marcados. */
    @Transaction
    suspend fun upsertKeepingFavorites(items: List<AsteroidEntity>) {
        val favs = favoriteIds().toSet()
        insertAll(items.map { it.copy(isFavorite = it.id in favs) })
    }
}
