package com.david.seriesapp.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.david.seriesapp.theme.Shapes
import com.david.seriesapp.theme.Typography

private val LightColorScheme = lightColorScheme(
    primary = TmdbPrimary,
    onPrimary = TextOnPrimary,
    primaryContainer = TmdbPrimary.copy(alpha = 0.1f),
    onPrimaryContainer = TmdbDarkBlue,

    secondary = TmdbSuccess,
    onSecondary = TextOnPrimary,
    secondaryContainer = TmdbSuccess.copy(alpha = 0.1f),
    onSecondaryContainer = TmdbDarkBlue,

    tertiary = TmdbWarning,
    onTertiary = TextPrimaryLight,
    tertiaryContainer = TmdbWarning.copy(alpha = 0.1f),
    onTertiaryContainer = TmdbDarkBlue,

    background = BackgroundLight,
    onBackground = TextPrimaryLight,

    surface = SurfaceLight,
    onSurface = TextOnSurface,

    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondaryLight,

    outline = BorderLight,
    outlineVariant = DividerLight,

    error = TmdbError,
    onError = TextOnPrimary,
    errorContainer = TmdbError.copy(alpha = 0.1f),
    onErrorContainer = TmdbError,

    scrim = OverlayLight,
    surfaceTint = TmdbPrimary
)

private val DarkColorScheme = darkColorScheme(
    primary = TmdbPrimaryDark,
    onPrimary = Color.White,
    primaryContainer = TmdbPrimaryDark.copy(alpha = 0.2f),
    onPrimaryContainer = Color.White,

    secondary = TmdbSuccessDark,
    onSecondary = Color.Black,
    secondaryContainer = TmdbSuccessDark.copy(alpha = 0.2f),
    onSecondaryContainer = TmdbSuccessDark,

    tertiary = TmdbWarningDark,
    onTertiary = Color.Black,
    tertiaryContainer = TmdbWarningDark.copy(alpha = 0.2f),
    onTertiaryContainer = TmdbWarningDark,

    background = BackgroundDark,
    onBackground = TextPrimaryDark,

    surface = SurfaceDark,
    onSurface = TextOnSurfaceDark,

    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,

    outline = BorderDark,
    outlineVariant = DividerDark,

    error = TmdbErrorDark,
    onError = Color.White,
    errorContainer = TmdbErrorDark.copy(alpha = 0.2f),
    onErrorContainer = TmdbErrorDark,

    scrim = OverlayDark,
    surfaceTint = TmdbPrimaryDark
)

@Composable
fun SeriesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}