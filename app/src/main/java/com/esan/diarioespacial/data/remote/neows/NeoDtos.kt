package com.esan.diarioespacial.data.remote.neows

import com.google.gson.annotations.SerializedName

/**
 * near_earth_objects viene AGRUPADO POR FECHA:
 * { "2026-11-14": [ {...}, {...} ], "2026-11-13": [ ... ] }
 * -> se modela como Map<String, List<NeoDto>?>
 * Todo nullable para que la app no se caiga con datos incompletos.
 */
data class NeoFeedDto(
    @SerializedName("element_count") val elementCount: Int?,
    @SerializedName("near_earth_objects") val nearEarthObjects: Map<String, List<NeoDto>?>?
)

data class NeoDto(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("is_potentially_hazardous_asteroid") val isHazardous: Boolean?,
    @SerializedName("estimated_diameter") val estimatedDiameter: EstimatedDiameterDto?,
    @SerializedName("close_approach_data") val closeApproachData: List<CloseApproachDto>?
)

data class EstimatedDiameterDto(
    @SerializedName("meters") val meters: DiameterRangeDto?
)

data class DiameterRangeDto(
    @SerializedName("estimated_diameter_min") val min: Double?,
    @SerializedName("estimated_diameter_max") val max: Double?
)

data class CloseApproachDto(
    @SerializedName("close_approach_date") val date: String?,
    @SerializedName("epoch_date_close_approach") val epoch: Long?,
    @SerializedName("relative_velocity") val relativeVelocity: RelativeVelocityDto?,
    @SerializedName("miss_distance") val missDistance: MissDistanceDto?
)

// Ojo: velocidad y distancia llegan como TEXTO
data class RelativeVelocityDto(
    @SerializedName("kilometers_per_hour") val kilometersPerHour: String?
)

data class MissDistanceDto(
    @SerializedName("kilometers") val kilometers: String?
)
