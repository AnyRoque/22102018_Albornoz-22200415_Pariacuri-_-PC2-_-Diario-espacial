package com.esan.diarioespacial.ui.asteroids

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

// Máximo 2 decimales (12.5 -> "12.5", 12.3456 -> "12.35"), con separador de miles
private val formatter = DecimalFormat("#,##0.##", DecimalFormatSymbols(Locale.US))

/** 1234.567.withUnit("km") -> "1,234.57 km". Si es null -> "No disponible". */
fun Double?.withUnit(unit: String): String =
    this?.let { "${formatter.format(it)} $unit" } ?: "No disponible"
