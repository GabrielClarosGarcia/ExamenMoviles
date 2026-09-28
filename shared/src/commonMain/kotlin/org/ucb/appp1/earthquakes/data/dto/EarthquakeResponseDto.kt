package org.ucb.appp1.earthquakes.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class EarthquakeResponseDto(
    val features: List<EarthquakeFeatureDto> = emptyList()
)
