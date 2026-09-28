package org.ucb.appp1.earthquakes.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import org.ucb.appp1.earthquakes.domain.model.EarthquakeModel
import org.ucb.appp1.earthquakes.presentation.viewmodel.EarthquakeEffect
import org.ucb.appp1.earthquakes.presentation.viewmodel.EarthquakeEvent
import org.ucb.appp1.earthquakes.presentation.viewmodel.EarthquakeViewModel

@Composable
fun EarthquakeScreen(
    viewModel: EarthquakeViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.emitEvent(EarthquakeEvent.OnLoad)
        viewModel.effect.collect { effect ->
            when (effect) {
                is EarthquakeEffect.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        when {
            state.value.isLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            }

            state.value.error != null && state.value.list.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(state.value.error ?: "Error")
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = { viewModel.emitEvent(EarthquakeEvent.OnLoad) }
                    ) {
                        Text("Reintentar")
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Text(
                            text = "USGS Earthquakes",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Terremotos de magnitud 5 o superior",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    items(state.value.list) { earthquake ->
                        EarthquakeCard(earthquake)
                    }
                }
            }
        }
    }
}

@Composable
private fun EarthquakeCard(earthquake: EarthquakeModel) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = earthquake.place,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text("Magnitud: ${earthquake.magnitude}")
            Text("Fecha y hora: ${earthquake.time}")
            HorizontalDivider()
            Text("Longitud: ${earthquake.longitude}")
            Text("Latitud: ${earthquake.latitude}")
            Text("Profundidad: ${earthquake.depth} km")
            Text(
                text = "Enlace: ${earthquake.url}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
