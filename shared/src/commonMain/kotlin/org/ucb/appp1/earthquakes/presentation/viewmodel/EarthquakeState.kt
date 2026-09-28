package org.ucb.appp1.earthquakes.presentation.viewmodel

import org.ucb.appp1.earthquakes.domain.model.EarthquakeModel

data class EarthquakeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val list: List<EarthquakeModel> = emptyList()
)
