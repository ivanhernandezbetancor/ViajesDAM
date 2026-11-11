package com.example.viajesdam.model

import kotlinx.coroutines.flow.Flow

class ViajeRepository(private val dao: ViajeDao) {

    val todosLosViajes: Flow<List<ViajeEntity>> = dao.obtenerTodos()

    suspend fun insertar(viaje: ViajeEntity) {
        dao.insertar(viaje)
    }

    suspend fun actualizar(viaje: ViajeEntity) {
        dao.actualizar(viaje)
    }

    suspend fun eliminar(viaje: ViajeEntity) {
        dao.eliminar(viaje)
    }

    suspend fun obtenerPorId(id: Int): ViajeEntity? {
        return dao.obtenerPorId(id)
    }
}