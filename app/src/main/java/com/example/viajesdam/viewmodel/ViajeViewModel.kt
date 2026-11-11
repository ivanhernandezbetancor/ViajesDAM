package com.example.viajesdam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.viajesdam.model.ViajeEntity
import com.example.viajesdam.model.ViajeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ViajeViewModel(private val repository: ViajeRepository) : ViewModel() {

    // Conversión del Flow de Room en StateFlow reactivo para Compose
    val listaViajes: StateFlow<List<ViajeEntity>> = repository.todosLosViajes
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun agregarViaje(destino: String, pais: String, anio: Int, esVisitado: Boolean) {
        viewModelScope.launch {
            val nuevo = ViajeEntity(
                destino = destino,
                pais = pais,
                anio = anio,
                esVisitado = esVisitado
            )
            repository.insertar(nuevo)
        }
    }

    fun conmutarFavorito(viaje: ViajeEntity) {
        viewModelScope.launch {
            val actualizado = viaje.copy(esFavorito = !viaje.esFavorito)
            repository.actualizar(actualizado)
        }
    }

    fun eliminarViaje(viaje: ViajeEntity) {
        viewModelScope.launch {
            repository.eliminar(viaje)
        }
    }
}