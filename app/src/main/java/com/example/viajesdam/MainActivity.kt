package com.example.viajesdam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viajesdam.ui.screens.AppViajesMainScreen
import com.example.viajesdam.ui.theme.ViajesDAMTheme
import com.example.viajesdam.viewmodel.ViajeViewModel
import com.example.viajesdam.viewmodel.ViajeViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Obtenemos el contenedor global de la aplicación
        val app = application as ViajesApplication

        // 2. Creamos la fábrica pasándole el repositorio almacenado en Application
        val factory = ViajeViewModelFactory(app.repository)

        setContent {
            ViajesDAMTheme {
                // 3. Compose obtiene y mantiene el ViewModel retenido ante rotaciones
                val viewModel: ViajeViewModel = viewModel(factory = factory)

                // 4. Invocamos la pantalla principal pasando el ViewModel
                AppViajesMainScreen(viewModel = viewModel)
            }
        }
    }
}