package org.ucb.appp1.earthquakes.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class EarthquakeFeatureDto(
    val properties: EarthquakePropertiesDto = EarthquakePropertiesDto(),
    val geometry: EarthquakeGeometryDto = EarthquakeGeometryDto()
)
