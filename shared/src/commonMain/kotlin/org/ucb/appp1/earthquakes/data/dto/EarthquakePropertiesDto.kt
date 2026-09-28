package org.ucb.appp1.earthquakes.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class EarthquakePropertiesDto(
    val place: String? = null,
    val mag: Double? = null,
    val time: Long? = null,
    val url: String? = null
)
