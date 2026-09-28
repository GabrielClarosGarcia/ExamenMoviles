package org.ucb.appp1

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import org.ucb.appp1.earthquakes.presentation.screen.EarthquakeScreen

@Composable
fun App() {
    MaterialTheme {
        EarthquakeScreen()
    }
}
