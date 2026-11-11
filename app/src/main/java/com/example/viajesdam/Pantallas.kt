package com.example.viajesdam

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.viajesdam.viewmodel.DetalleViajeViewModel
import com.example.viajesdam.viewmodel.DetalleUiState
import com.example.viajesdam.viewmodel.ViajeViewModel

// Pantalla de la ruta inicio -> mensaje de bienvenida
@Composable
fun PantallaInicio() {
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.inicio_bienvenida),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
    }
}

// Pantalla de perfil: cabecera con degradado, avatar redondo con borde y estadísticas de Room
@Composable
fun PantallaPerfil(
    viewModel: ViajeViewModel,
    modifier: Modifier = Modifier
) {
    val viajes by viewModel.listaViajes.collectAsStateWithLifecycle()
    val total = viajes.size
    val favoritos = viajes.count { it.esFavorito }
    val visitados = viajes.count { it.esVisitado }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cabecera con degradado y avatar superpuesto
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                ),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .offset(y = 60.dp)
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
                    .border(4.dp, MaterialTheme.colorScheme.surface, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(R.string.avatar_desc),
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.height(72.dp))

        Text(
            text = stringResource(R.string.user_name),
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = stringResource(R.string.user_rol),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(R.string.user_bio),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TarjetaEstadistica(
                valor = total,
                etiqueta = stringResource(R.string.estadistica_viajes),
                modifier = Modifier.weight(1f)
            )
            TarjetaEstadistica(
                valor = favoritos,
                etiqueta = stringResource(R.string.estadistica_favoritos),
                modifier = Modifier.weight(1f)
            )
            TarjetaEstadistica(
                valor = visitados,
                etiqueta = stringResource(R.string.estadistica_visitados),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// Tarjeta con un número grande y su etiqueta
@Composable
fun TarjetaEstadistica(
    valor: Int,
    etiqueta: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = valor.toString(),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = etiqueta,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

// Pantalla de detalle (Maestro-Detalle)
@Composable
fun PantallaDetalleScreen(
    viajeId: Int,
    viewModel: DetalleViajeViewModel,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mensajeNoEncontrado = stringResource(R.string.detalle_no_encontrado)

    LaunchedEffect(viajeId) {
        viewModel.cargarDetalleViaje(viajeId, mensajeNoEncontrado)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = onVolver) {
            Text(text = stringResource(R.string.detalle_volver))
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {
            is DetalleUiState.Cargando -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = stringResource(R.string.detalle_cargando))
                    }
                }
            }
            is DetalleUiState.Exito -> {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = state.viaje.destino,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.detalle_pais, state.viaje.pais),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = stringResource(R.string.detalle_anio, state.viaje.anio),
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = if (state.viaje.esVisitado) stringResource(R.string.detalle_visitado)
                            else stringResource(R.string.detalle_pendiente),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Text(
                            text = if (state.viaje.esFavorito) stringResource(R.string.detalle_favorito)
                            else stringResource(R.string.detalle_no_favorito),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            is DetalleUiState.Error -> {
                Text(
                    text = state.mensaje,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}