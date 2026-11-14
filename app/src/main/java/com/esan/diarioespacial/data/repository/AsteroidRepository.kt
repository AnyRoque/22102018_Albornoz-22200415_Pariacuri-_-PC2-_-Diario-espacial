package com.esan.diarioespacial.data.repository

import com.esan.diarioespacial.data.local.AsteroidDao
import com.esan.diarioespacial.data.local.AsteroidEntity
import com.esan.diarioespacial.data.mapper.toEntities
import com.esan.diarioespacial.data.remote.neows.NeoWsApi
import kotlinx.coroutines.flow.Flow

class AsteroidRepository(
    private val api: NeoWsApi,
    private val dao: AsteroidDao
) {
    fun observeRange(start: String, end: String): Flow<List<AsteroidEntity>> = dao.observeRange(start, end)
    fun observeFavorites(): Flow<List<AsteroidEntity>> = dao.observeFavorites()
    fun observeById(id: String): Flow<AsteroidEntity?> = dao.observeById(id)

    /** Consulta la API y guarda en Room. La UI se actualiza sola por el Flow. */
    suspend fun refresh(start: String, end: String) {
        val feed = api.getFeed(start, end)
        dao.upsertKeepingFavorites(feed.toEntities())
    }

    suspend fun toggleFavorite(item: AsteroidEntity) = dao.setFavorite(item.id, !item.isFavorite)
}
