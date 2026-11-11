package com.example.viajesdam.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.viajesdam.R
import com.example.viajesdam.model.ViajeEntity
import com.example.viajesdam.viewmodel.ViajeViewModel

@Composable
fun ListaViajesScreen(
    viewModel: ViajeViewModel,
    onViajeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Recolección reactiva del StateFlow alimentado por el Flow de Room
    val viajes by viewModel.listaViajes.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = viajes,
            key = { j -> j.id } // PrimaryKey autogenerada por Room
        ) { viaje ->
            TarjetaViajeItem(
                viaje = viaje,
                onClick = { onViajeClick(viaje.id) },
                onToggleFavorito = { viewModel.conmutarFavorito(viaje) },
                onEliminar = { viewModel.eliminarViaje(viaje) }
            )
        }
    }
}

@Composable
fun TarjetaViajeItem(
    viaje: ViajeEntity,
    onClick: () -> Unit,
    onToggleFavorito: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = viaje.destino,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(R.string.pais_anio, viaje.pais, viaje.anio),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = onToggleFavorito) {
                Icon(
                    imageVector = if (viaje.esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.favorito),
                    tint = if (viaje.esFavorito) Color.Red else Color.Gray
                )
            }
            IconButton(onClick = onEliminar) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = stringResource(R.string.eliminar_viaje)
                )
            }
        }
    }
}