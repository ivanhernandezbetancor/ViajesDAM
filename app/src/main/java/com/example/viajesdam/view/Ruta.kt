package com.example.viajesdam.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.viajesdam.R

sealed class Ruta(val path: String, val titulo: Int, val icono: ImageVector) {
    object Inicio : Ruta("inicio", R.string.menu_inicio, Icons.Filled.Home)
    object Catalogo : Ruta("catalogo", R.string.menu_catalogo, Icons.AutoMirrored.Filled.List)
    object Formulario : Ruta("alta", R.string.menu_anadir_viaje, Icons.Filled.Add)
    object Perfil : Ruta("perfil", R.string.menu_perfil, Icons.Filled.Person)

    // Ruta dinámica con parámetro de ID (en minúsculas)
    object Detalle : Ruta("detalle/{viajeId}", R.string.menu_detalle, Icons.Filled.Details) {
        fun crearRuta(viajeId: Int) = "detalle/$viajeId"
    }
}