package com.esan.diarioespacial.ui.asteroids

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.esan.diarioespacial.ui.components.EmptyView
import com.esan.diarioespacial.ui.components.LoadingView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsteroidListScreen(
    viewModel: AsteroidViewModel,
    onOpenDetail: (String) -> Unit,
    onOpenFavorites: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Asteroides") },
                actions = {
                    IconButton(onClick = onOpenFavorites) {
                        Icon(Icons.Filled.Favorite, contentDescription = "Mis asteroides")
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {

            // Filtro en la parte superior de la lista
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Solo potencialmente peligrosos", Modifier.weight(1f))
                Switch(checked = state.onlyHazardous, onCheckedChange = viewModel::setOnlyHazardous)
            }

            if (state.isLoading && state.items.isNotEmpty()) {
                LinearProgressIndicator(Modifier.fillMaxWidth())
            }

            when {
                state.isLoading && state.items.isEmpty() -> LoadingView()
                state.items.isEmpty() -> EmptyView(
                    message = state.error
                        ?: if (state.onlyHazardous) "No hay asteroides potencialmente peligrosos en estos días."
                        else "No hay asteroides para mostrar.",
                    onRetry = viewModel::refresh
                )
                else -> LazyColumn(Modifier.fillMaxSize()) {
                    items(state.items, key = { it.id }) { item ->
                        AsteroidItem(
                            item = item,
                            onClick = { onOpenDetail(item.id) },
                            onToggleFavorite = { viewModel.toggleFavorite(item) }
                        )
                    }
                }
            }
        }
    }
}
