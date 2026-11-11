package com.example.viajesdam.viewmodel

import com.example.viajesdam.model.ViajeEntity

sealed interface DetalleUiState {
    object Cargando : DetalleUiState
    data class Exito(val viaje: ViajeEntity) : DetalleUiState
    data class Error(val mensaje: String) : DetalleUiState
}