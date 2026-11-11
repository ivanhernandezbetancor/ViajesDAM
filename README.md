<div align="center">

# 🧳 ViajesDAM · Actividad de repaso

**Diario de viajes con menú lateral, Room, navegación con parámetros y corrutinas**

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Material 3](https://img.shields.io/badge/Material%203-757575?style=for-the-badge&logo=materialdesign&logoColor=white)
![Room](https://img.shields.io/badge/Room-003B57?style=for-the-badge&logo=sqlite&logoColor=white)
![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84?style=for-the-badge&logo=androidstudio&logoColor=white)

**Módulo:** Programación Multimedia y Dispositivos Móviles (2º DAM)  
**Unidad de Trabajo Nº2:** Desarrollo de aplicaciones Android  
**Actividad de repaso:** Diario de viajes con arquitectura MVVM + Room, `StateFlow` y pantalla de detalle asíncrona  

</div>

---

## 🎯 Objetivo

Desarrollar **ViajesDAM**, una aplicación Android para almacenar y consultar los viajes de un diario personal. La actividad sigue la misma arquitectura por capas que DAMFlix (Parte 7): **MVVM + Room**, con `ViewModel` y `Factory` independientes, rutas en una `sealed class`, un único `Pantallas.kt` en la raíz y las pantallas de catálogo y formulario en `ui/screens`.

## ✅ Requisitos funcionales

| Requisito | Cómo se cumple |
|---|---|
| **1. Inicio** | Pantalla de bienvenida con el nombre de la app |
| **2. Alta de viaje** | Formulario con destino, país, año del viaje y casilla "Ya lo he visitado". El botón Guardar solo se habilita si destino y país no están vacíos y el año es un número válido |
| **3. Catálogo** | `LazyColumn` con todos los viajes. Cada tarjeta tiene un icono de **favorito** y otro de **eliminar**, y toda la tarjeta es clicable |
| **4. Detalle (Maestro-Detalle)** | Al pulsar una tarjeta se navega a `"detalle/{viajeId}"`. La carga se hace con una corrutina en `Dispatchers.IO` y la pantalla muestra `Cargando`, `Exito` o `Error` |
| **5. Perfil** | Cabecera con degradado, avatar circular con borde, nombre, rol, descripción y tres tarjetas de estadísticas (viajes, favoritos y visitados) calculadas con los datos de Room |
| **6. Menú hamburguesa** | `ModalNavigationDrawer` con Inicio, Catálogo, Añadir Viaje y Perfil. El detalle no aparece en el menú |

## 📐 Requisitos técnicos

* Paquete base `com.example.viajesdam`.
* Prohibido escribir cadenas hardcoded: todos los textos de la interfaz en `strings.xml` y `stringResource()`.
* Clases de Room en el paquete `model`, junto al repositorio.
* `Flow` en el DAO y `StateFlow` en el ViewModel con `stateIn`.
* Base de datos y repositorio creados en una clase `Application` con `by lazy`.
* Cada ViewModel tiene su propia clase `ViewModelProvider.Factory`.
* Las pantallas no reciben el `NavController`: reciben lambdas (*event hoisting*).
* La lectura de Room del detalle se ejecuta con `withContext(Dispatchers.IO)`.
* Estados del detalle con una `sealed interface` (`Cargando`, `Exito`, `Error`).
* El mensaje de error llega al ViewModel como parámetro, porque el ViewModel no puede usar `stringResource()`.

<div align="center">

[![Ver Presentación Web](https://img.shields.io/badge/🌐_Ver_Presentación_Web-ViajesDAM-0b6e6e?style=for-the-badge)](https://ivanhernandezbetancor.github.io/ViajesDAM/)

</div>
