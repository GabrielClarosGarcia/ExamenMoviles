package org.ucb.appp1.earthquakes.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.ucb.appp1.earthquakes.domain.usecase.GetEarthquakesUseCase

class EarthquakeViewModel(
    private val getEarthquakesUseCase: GetEarthquakesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(EarthquakeState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<EarthquakeEffect>()
    val effect = _effect.asSharedFlow()

    fun emitEvent(event: EarthquakeEvent) {
        when (event) {
            EarthquakeEvent.OnLoad -> loadEarthquakes()
        }
    }

    private fun loadEarthquakes() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            getEarthquakesUseCase.invoke().fold(
                onSuccess = { earthquakes ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            list = earthquakes,
                            error = null
                        )
                    }
                },
                onFailure = { throwable ->
                    val message = throwable.message ?: "No se pudieron cargar los terremotos"
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = message
                        )
                    }
                    emitEffect(EarthquakeEffect.ShowSnackbar(message))
                }
            )
        }
    }

    private fun emitEffect(effect: EarthquakeEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}
