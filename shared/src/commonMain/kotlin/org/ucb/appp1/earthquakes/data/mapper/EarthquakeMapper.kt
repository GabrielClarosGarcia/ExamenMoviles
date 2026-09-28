package org.ucb.appp1.earthquakes.data.mapper

import org.ucb.appp1.earthquakes.data.dto.EarthquakeFeatureDto
import org.ucb.appp1.earthquakes.domain.model.EarthquakeModel

fun EarthquakeFeatureDto.toDomain(): EarthquakeModel = EarthquakeModel(
    place = properties.place ?: "Sin ubicación",
    magnitude = properties.mag ?: 0.0,
    time = properties.time ?: 0L,
    url = properties.url ?: "",
    longitude = geometry.coordinates.getOrNull(0) ?: 0.0,
    latitude = geometry.coordinates.getOrNull(1) ?: 0.0,
    depth = geometry.coordinates.getOrNull(2) ?: 0.0
)
