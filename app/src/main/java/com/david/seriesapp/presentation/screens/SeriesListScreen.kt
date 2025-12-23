package com.david.seriesapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.david.seriesapp.data.remote.TvSeriesDto
import com.david.seriesapp.presentation.components.LoadingItem
import com.david.seriesapp.presentation.navigation.Routes
import com.david.seriesapp.presentation.viewmodels.TvSeriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeriesListScreen(
    navController: NavController,
    viewModel: TvSeriesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    // Detecta cuando el usuario está cerca del final de la lista
    val isNearBottom by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
            lastVisibleItem != null && lastVisibleItem.index >= listState.layoutInfo.totalItemsCount - 3
        }
    }

    // Carga más series cuando el usuario está cerca del final
    LaunchedEffect(isNearBottom) {
        if (isNearBottom && !uiState.isLoadingMore && !uiState.isLoading) {
            viewModel.loadMoreSeries()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Series Populares") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.refreshSeries() },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Refresh, contentDescription = "Recargar")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Estado de carga inicial
            if (uiState.isLoading && uiState.series.isEmpty()) {
                LoadingItem(
                    modifier = Modifier.align(Alignment.Center),
                    message = "Cargando series..."
                )
            }
            // Estado de error
            else if (uiState.error != null && uiState.series.isEmpty()) {
                uiState.error?.let { errorMessage ->
                    ListErrorState(
                        message = errorMessage,
                        onRetry = { viewModel.loadInitialSeries() }
                    )
                }
            }
            // Lista de series
            else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.series) { serie ->
                        SeriesCard(serie = serie) {
                            navController.navigate(Routes.SeriesDetail.createRoute(serie.id))
                        }
                    }

                    // Carga al final para paginación
                    if (uiState.isLoadingMore) {
                        item {
                            LoadingItem(message = "Cargando más series...")
                        }
                    }

                    // Mensaje cuando no hay más series
                    if (!uiState.isLoadingMore && uiState.series.isNotEmpty()) {
                        item {
                            EndOfListMessage()
                        }
                    }
                }
            }

            // Error overlay (para errores durante la paginación)
            if (uiState.error != null && uiState.series.isNotEmpty()) {
                uiState.error?.let { errorMessage ->
                    ListErrorSnackbar(
                        message = errorMessage,
                        onDismiss = { /* Podríamos resetear el error en el ViewModel */ }
                    )
                }
            }
        }
    }
}

@Composable
fun SeriesCard(
    serie: TvSeriesDto,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = serie.fullPosterPath,
                contentDescription = "Póster de ${serie.name}",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = serie.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = serie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    AssistChip(
                        onClick = {},
                        label = { Text("⭐ ${String.format("%.1f", serie.voteAverage)}/10") },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    )

                    if (serie.firstAirDate != null) {
                        AssistChip(
                            onClick = {},
                            label = { Text(serie.firstAirDate.take(4)) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = MaterialTheme.colorScheme.tertiaryContainer
                            )
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ListErrorState(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Error al cargar",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 16.dp),
            textAlign = TextAlign.Center
        )
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}

@Composable
fun EndOfListMessage() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No hay más series para mostrar",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun ListErrorSnackbar(
    message: String,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.errorContainer,
            contentColor = MaterialTheme.colorScheme.onErrorContainer,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = onDismiss) {
                    Text("OK")
                }
            }
        }
    }
}