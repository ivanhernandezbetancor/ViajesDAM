package com.example.viajesdam.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ViajeDao {

    // Al retornar Flow, Room notifica automáticamente cualquier cambio en la tabla
    @Query("SELECT * FROM viajes ORDER BY id DESC")
    fun obtenerTodos(): Flow<List<ViajeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(viaje: ViajeEntity)

    @Update
    suspend fun actualizar(viaje: ViajeEntity)

    @Delete
    suspend fun eliminar(viaje: ViajeEntity)

    // Consulta de un único viaje para la pantalla de detalle
    @Query("SELECT * FROM viajes WHERE id = :id")
    suspend fun obtenerPorId(id: Int): ViajeEntity?
}