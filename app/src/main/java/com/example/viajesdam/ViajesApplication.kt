package com.example.viajesdam

import android.app.Application
import com.example.viajesdam.model.AppDatabase
import com.example.viajesdam.model.ViajeRepository

class ViajesApplication : Application() {

    // 1. La base de datos solo se crea cuando se accede a ella por primera vez
    val database by lazy { AppDatabase.getDatabase(this) }

    // 2. El repositorio se crea cuando se necesite, recibiendo el DAO de la BD perezosa
    val repository by lazy { ViajeRepository(database.viajeDao()) }
}