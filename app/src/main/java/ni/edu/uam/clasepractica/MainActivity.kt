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
            MaterialTheme {
                AppTareas()
            }
        }
    }
}

// MODELO
data class Tarea(
    val nombre: String,
    val fecha: String,
    var estado: String = "En espera"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTareas() {

    var texto by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("Agrega una tarea") }

    val listaTareas = remember { mutableStateListOf<Tarea>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestor de Tareas") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            // INPUT NOMBRE
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it },
                label = { Text("Nombre de la tarea") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // INPUT FECHA (SOLO TEXTO)
            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha de entrega") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            // BOTÓN
            Button(
                onClick = {
                    if (texto.isNotEmpty() && fecha.isNotEmpty()) {
                        listaTareas.add(Tarea(texto, fecha))
                        mensaje = "Tarea agregada ✅"
                        texto = ""
                        fecha = ""
                    } else {
                        mensaje = "Completa todos los campos ⚠️"
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

            // LISTA
            LazyColumn {
                items(listaTareas) { tarea ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                            .clickable {

                                // CAMBIO MANUAL DE ESTADO
                                tarea.estado = when (tarea.estado) {
                                    "En espera" -> "En proceso"
                                    "En proceso" -> "Finalizado"
                                    else -> "En espera"
                                }

                                mensaje = "Estado actualizado 🔄"
                            }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = tarea.nombre)
                            Text(text = "Fecha: ${tarea.fecha}")
                            Text(text = "Estado: ${tarea.estado}")
                        }
                    }
                }
            }
        }
    }
}