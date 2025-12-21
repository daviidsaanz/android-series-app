package com.david.seriesapp.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

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

@Composable
fun SeriesAppTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}