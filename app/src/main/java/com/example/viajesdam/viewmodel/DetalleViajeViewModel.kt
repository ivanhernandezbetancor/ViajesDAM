package com.example.viajesdam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viajesdam.model.ViajeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalleViajeViewModel(
    private val repository: ViajeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetalleUiState>(DetalleUiState.Cargando)
    val uiState: StateFlow<DetalleUiState> = _uiState.asStateFlow()

    fun cargarDetalleViaje(viajeId: Int, mensajeNoEncontrado: String) {
        viewModelScope.launch {
            _uiState.value = DetalleUiState.Cargando

            val resultado = withContext(Dispatchers.IO) {
                delay(1000) // Simulación de retardo para apreciar el indicador de carga
                repository.obtenerPorId(viajeId)
            }

            if (resultado != null) {
                _uiState.value = DetalleUiState.Exito(resultado)
            } else {
                _uiState.value = DetalleUiState.Error(mensajeNoEncontrado)
            }
        }
    }
}