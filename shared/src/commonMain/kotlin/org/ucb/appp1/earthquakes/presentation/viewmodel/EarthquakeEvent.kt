package org.ucb.appp1.earthquakes.presentation.viewmodel

sealed interface EarthquakeEvent {
    data object OnLoad : EarthquakeEvent
}
