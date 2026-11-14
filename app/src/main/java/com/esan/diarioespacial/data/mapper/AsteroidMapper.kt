package com.esan.diarioespacial.data.mapper

import com.esan.diarioespacial.data.local.AsteroidEntity
import com.esan.diarioespacial.data.remote.neows.NeoDto
import com.esan.diarioespacial.data.remote.neows.NeoFeedDto

/** Aplana el mapa {fecha -> lista} en una sola lista de Entities. */
fun NeoFeedDto.toEntities(): List<AsteroidEntity> =
    nearEarthObjects.orEmpty().flatMap { (feedDate, list) ->
        list.orEmpty().mapNotNull { it.toEntityOrNull(feedDate) }
    }

/** DTO -> Entity. Si falta el id (clave primaria) se descarta. */
fun NeoDto.toEntityOrNull(feedDate: String): AsteroidEntity? {
    val safeId = id ?: return null
    val approach = closeApproachData?.firstOrNull()   // puede venir vacía
    return AsteroidEntity(
        id = safeId,
        name = name ?: "Sin nombre",
        approachDate = approach?.date ?: feedDate,
        approachEpoch = approach?.epoch ?: 0L,
        diameterMinM = estimatedDiameter?.meters?.min,
        diameterMaxM = estimatedDiameter?.meters?.max,
        velocityKmh = approach?.relativeVelocity?.kilometersPerHour?.toDoubleOrNull(), // texto -> número
        missDistanceKm = approach?.missDistance?.kilometers?.toDoubleOrNull(),         // texto -> número
        isHazardous = isHazardous ?: false
    )
}
