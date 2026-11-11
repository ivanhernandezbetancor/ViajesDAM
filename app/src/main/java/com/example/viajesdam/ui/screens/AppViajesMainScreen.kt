package com.example.viajesdam.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.viajesdam.ViajesApplication
import com.example.viajesdam.PantallaDetalleScreen
import com.example.viajesdam.PantallaInicio
import com.example.viajesdam.PantallaPerfil
import com.example.viajesdam.R
import com.example.viajesdam.view.Ruta
import com.example.viajesdam.viewmodel.DetalleViajeViewModel
import com.example.viajesdam.viewmodel.DetalleViajeViewModelFactory
import com.example.viajesdam.viewmodel.ViajeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppViajesMainScreen(viewModel: ViajeViewModel) {
    val navController = rememberNavController()

    // 1. Estado del Drawer (abierto / cerrado)
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    // 2. CoroutineScope necesario para animar la apertura y el cierre
    val coroutineScope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    val opcionesMenu = listOf(Ruta.Inicio, Ruta.Catalogo, Ruta.Formulario, Ruta.Perfil)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // Cabecera del menú lateral
                Box(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                    Text(
                        text = stringResource(R.string.cabecera_drawer),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))

                // Opciones del menú
                opcionesMenu.forEach { opcion ->
                    NavigationDrawerItem(
                        icon = { Icon(opcion.icono, contentDescription = stringResource(opcion.titulo)) },
                        label = { Text(stringResource(opcion.titulo)) },
                        selected = rutaActual == opcion.path,
                        onClick = {
                            // Cierre asíncrono del panel
                            coroutineScope.launch { drawerState.close() }
                            if (rutaActual != opcion.path) {
                                navController.navigate(opcion.path) {
                                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        // Contenido principal envuelto en Scaffold
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.titulo_app)) },
                    navigationIcon = {
                        // Botón de hamburguesa para abrir el Drawer
                        IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = stringResource(R.string.abrir_menu))
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Ruta.Inicio.path,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Ruta.Inicio.path) {
                    PantallaInicio()
                }

                composable(Ruta.Catalogo.path) {
                    ListaViajesScreen(
                        viewModel = viewModel,
                        onViajeClick = { viajeId ->
                            navController.navigate(Ruta.Detalle.crearRuta(viajeId))
                        }
                    )
                }

                composable(Ruta.Formulario.path) {
                    FormularioAgregarViajeScreen(
                        viewModel = viewModel,
                        onViajeGuardado = {
                            navController.navigate(Ruta.Catalogo.path) {
                                popUpTo(Ruta.Inicio.path) { saveState = true }
                            }
                        }
                    )
                }

                composable(Ruta.Perfil.path) {
                    PantallaPerfil(viewModel = viewModel)
                }

                composable(
                    route = Ruta.Detalle.path,
                    arguments = listOf(
                        navArgument("viajeId") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("viajeId") ?: 0
                    val context = LocalContext.current
                    val app = context.applicationContext as ViajesApplication
                    val detalleViewModel: DetalleViajeViewModel = viewModel(
                        factory = DetalleViajeViewModelFactory(app.repository)
                    )
                    PantallaDetalleScreen(
                        viajeId = id,
                        viewModel = detalleViewModel,
                        onVolver = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}