package org.ucb.appp1.earthquakes.presentation.viewmodel

sealed interface EarthquakeEffect {
    data class ShowSnackbar(val message: String) : EarthquakeEffect
}
