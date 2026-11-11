package com.example.viajesdam.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.viajesdam.model.ViajeRepository

class DetalleViajeViewModelFactory(
    private val repository: ViajeRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetalleViajeViewModel::class.java)) {
            return DetalleViajeViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase ViewModel no conocida: ${modelClass.name}")
    }
}