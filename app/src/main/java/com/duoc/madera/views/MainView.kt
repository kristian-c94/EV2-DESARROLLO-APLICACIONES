package com.duoc.madera.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.madera.ui.components.AnimatedWoodTextButton
import com.duoc.madera.viewmodels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView(
    usuarioConectado: String,
    onCerrarSesion: () -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    val maderas by viewModel.maderas.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Enciclopedia de Maderas") },
                actions = {
                    AnimatedWoodTextButton(onClick = onCerrarSesion) {
                        Text("Salir", color = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "¡Bienvenido, $usuarioConectado!",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(maderas) { madera ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(madera.nombre, style = MaterialTheme.typography.titleLarge)
                            Text("${madera.tipo} • ${madera.descripcion}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
