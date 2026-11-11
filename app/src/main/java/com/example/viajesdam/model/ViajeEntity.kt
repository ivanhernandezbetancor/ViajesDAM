package com.example.viajesdam.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "viajes")
data class ViajeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val destino: String,
    val pais: String,
    val anio: Int,
    val esVisitado: Boolean = false,
    val esFavorito: Boolean = false
)

