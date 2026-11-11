package com.example.viajesdam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viajesdam.model.ViajeRepository

class ViajeViewModelFactory(
    private val repository: ViajeRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Validamos que se nos pida instanciar nuestro ViajeViewModel
        if (modelClass.isAssignableFrom(ViajeViewModel::class.java)) {
            return ViajeViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel no reconocida por la fábrica: ${modelClass.name}")
    }
}