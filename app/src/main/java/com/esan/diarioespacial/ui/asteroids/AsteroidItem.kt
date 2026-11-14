package com.esan.diarioespacial.ui.asteroids

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.esan.diarioespacial.data.local.AsteroidEntity
import com.esan.diarioespacial.ui.components.FavoriteButton

/** Elemento de lista: lo usan la lista principal y "Mis asteroides". */
@Composable
fun AsteroidItem(item: AsteroidEntity, onClick: () -> Unit, onToggleFavorite: () -> Unit) {
    // Peligrosos: fondo rojizo + etiqueta
    val colors = if (item.isHazardous) {
        CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
    } else {
        CardDefaults.cardColors()
    }

    Card(
        colors = colors,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable(onClick = onClick)
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                if (item.isHazardous) {
                    Text(
                        "⚠ POTENCIALMENTE PELIGROSO",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(item.name, style = MaterialTheme.typography.titleMedium)
                Text("Aproximación: ${item.approachDate}", style = MaterialTheme.typography.bodySmall)
                Text("Diámetro máx.: ${item.diameterMaxM.withUnit("m")}", style = MaterialTheme.typography.bodySmall)
                Text("Velocidad: ${item.velocityKmh.withUnit("km/h")}", style = MaterialTheme.typography.bodySmall)
                Text("Distancia: ${item.missDistanceKm.withUnit("km")}", style = MaterialTheme.typography.bodySmall)
            }
            FavoriteButton(isFavorite = item.isFavorite, onClick = onToggleFavorite)
        }
    }
}
