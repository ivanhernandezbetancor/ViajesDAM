package com.example.viajesdam.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.viajesdam.R
import com.example.viajesdam.viewmodel.ViajeViewModel

@Composable
fun FormularioAgregarViajeScreen(
    viewModel: ViajeViewModel,
    onViajeGuardado: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var destino by remember { mutableStateOf("") }
    var pais by remember { mutableStateOf("") }
    var anioTexto by remember { mutableStateOf("") }
    var esVisitado by remember { mutableStateOf(false) }

    val esFormularioValido = destino.isNotBlank() &&
            pais.isNotBlank() &&
            anioTexto.toIntOrNull() != null

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(R.string.anadir_viaje),
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = destino,
            onValueChange = { destino = it },
            label = { Text(stringResource(R.string.destino_viaje)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = pais,
            onValueChange = { pais = it },
            label = { Text(stringResource(R.string.pais_viaje)) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = anioTexto,
            onValueChange = { anioTexto = it },
            label = { Text(stringResource(R.string.anio_viaje)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(checked = esVisitado, onCheckedChange = { esVisitado = it })
            Text(text = stringResource(R.string.viaje_visitado))
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val anio = anioTexto.toIntOrNull() ?: 0
                viewModel.agregarViaje(
                    destino = destino,
                    pais = pais,
                    anio = anio,
                    esVisitado = esVisitado
                )
                destino = ""
                pais = ""
                anioTexto = ""
                esVisitado = false
                onViajeGuardado()
            },
            enabled = esFormularioValido,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.guardar_viaje))
        }
    }
}