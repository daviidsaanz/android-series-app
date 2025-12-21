package com.david.seriesapp.presentation.theme

import androidx.compose.ui.graphics.Color


// PALETA VERSIÓN 1.0 - MODO CLARO

val TmdbPrimary = Color(0xFF01B4E4)      // Azul brillante TMDB
val TmdbDarkBlue = Color(0xFF032541)     // Azul oscuro TMDB
val TmdbSuccess = Color(0xFF21D07A)      // Verde para ratings/éxito
val TmdbWarning = Color(0xFFD2D531)      // Amarillo para advertencias
val TmdbError = Color(0xFFDB2360)        // Rojo para errores

// Colores de fondo y superficie
val BackgroundLight = Color(0xFFF8F9FA)  // Fondo gris muy claro
val SurfaceLight = Color(0xFFFFFFFF)     // Superficie/tarjetas blanca
val SurfaceVariantLight = Color(0xFFE9ECEF) // Variante de superficie (bordes)

// Colores de texto
val TextPrimaryLight = Color(0xFF212529)     // Texto principal oscuro
val TextSecondaryLight = Color(0xFF6C757D)   // Texto secundario
val TextDisabledLight = Color(0xFFADB5BD)    // Texto deshabilitado
val TextOnPrimary = Color(0xFFFFFFFF)        // Texto sobre fondo primary
val TextOnSurface = Color(0xFF212529)        // Texto sobre superficie

// Colores de elementos UI
val BorderLight = Color(0xFFE9ECEF)          // Bordes y separadores
val DividerLight = Color(0xFFDEE2E6)         // Divisores
val OverlayLight = Color(0x66000000)         // Overlay semitransparente

// Colores de estado
val SuccessColor = Color(0xFF28A745)         // Éxito
val WarningColor = Color(0xFFFFC107)         // Advertencia
val ErrorColor = Color(0xFFDC3545)           // Error
val InfoColor = Color(0xFF17A2B8)            // Información

// Colores de interacción
val RippleColor = Color(0x1A01B4E4)          // Color de ripple effect
val FocusColor = Color(0x3301B4E4)           // Color de focus

// Colores de rating (estrellas)
val RatingEmpty = Color(0xFFE9ECEF)          // Estrella vacía
val RatingFull = Color(0xFFFFD700)           // Estrella llena (dorado)

// Colores de género (pueden variar)
val GenreAction = Color(0xFFFF6B6B)          // Acción - rojo
val GenreComedy = Color(0xFFFFD93D)          // Comedia - amarillo
val GenreDrama = Color(0xFF6BCF7F)           // Drama - verde
val GenreSciFi = Color(0xFF4D96FF)           // Sci-Fi - azul
val GenreFantasy = Color(0xFF9D65C9)         // Fantasía - púrpura

// Gradientes comunes
val PrimaryGradientStart = TmdbPrimary
val PrimaryGradientEnd = Color(0xFF1C7ED6)