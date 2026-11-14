package com.esan.diarioespacial.ui.asteroids

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.esan.diarioespacial.ui.components.EmptyView
import com.esan.diarioespacial.ui.components.FavoriteButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsteroidDetailScreen(viewModel: AsteroidViewModel, id: String, onBack: () -> Unit) {
    val item by remember(id) { viewModel.observeById(id) }.collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del asteroide") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    item?.let { a -> FavoriteButton(isFavorite = a.isFavorite) { viewModel.toggleFavorite(a) } }
                }
            )
        }
    ) { padding ->
        val a = item
        if (a == null) {
            EmptyView("No se encontró el asteroide.", Modifier.padding(padding))
        } else {
            Column(
                Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(a.name, style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(12.dp))
                DetailRow("ID", a.id)
                DetailRow("Fecha de aproximación", a.approachDate)
                DetailRow("Diámetro mínimo estimado", a.diameterMinM.withUnit("m"))
                DetailRow("Diámetro máximo estimado", a.diameterMaxM.withUnit("m"))
                DetailRow("Velocidad relativa", a.velocityKmh.withUnit("km/h"))
                DetailRow("Distancia de paso", a.missDistanceKm.withUnit("km"))
                DetailRow(
                    label = "Potencialmente peligroso",
                    value = if (a.isHazardous) "Sí" else "No",
                    valueColor = if (a.isHazardous) MaterialTheme.colorScheme.error else Color.Unspecified
                )
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String, valueColor: Color = Color.Unspecified) {
    Column(Modifier.padding(vertical = 6.dp)) {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, color = valueColor)
        HorizontalDivider(Modifier.padding(top = 6.dp))
    }
}
