package com.example.tareasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTareas()
        }
    }
}

// MODELO DE TAREA
data class Tarea(
    val nombre: String,
    var estado: String = "En espera"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTareas() {

    var texto by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("Agrega una tarea") }
    val listaTareas = remember { mutableStateListOf<Tarea>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestor de Tareas") }
            )
        }
    ) { padding ->

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            Column(modifier = Modifier.padding(16.dp)) {

                // INPUT
                OutlinedTextField(
                    value = texto,
                    onValueChange = { texto = it },
                    label = { Text("Nueva tarea") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                // BOTÓN
                Button(
                    onClick = {
                        if (texto.isNotEmpty()) {
                            listaTareas.add(Tarea(texto))
                            mensaje = "Tarea agregada "
                            texto = ""
                        } else {
                            mensaje = "Escribe algo primero "
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Agregar tarea")
                }

                Spacer(modifier = Modifier.height(10.dp))

                // MENSAJE DINÁMICO
                Text(text = mensaje)

                Spacer(modifier = Modifier.height(10.dp))

                // LISTA DE TAREAS
                LazyColumn {
                    items(listaTareas) { tarea ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .clickable {

                                    // CAMBIAR ESTADO AL TOCAR
                                    tarea.estado = when (tarea.estado) {
                                        "En espera" -> "En proceso"
                                        "En proceso" -> "Finalizado"
                                        else -> "En espera"
                                    }

                                    mensaje = "Estado actualizado "
                                }
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = tarea.nombre)
                                Text(text = "Estado: ${tarea.estado}")
                            }
                        }
                    }
                }
            }
        }
    }
}