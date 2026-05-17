package com.willydev.rickandmortyapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import com.willydev.rickandmortyapp.R
import com.willydev.rickandmortyapp.data.model.Character
import com.willydev.rickandmortyapp.ui.components.GlassmorphismCard
import com.willydev.rickandmortyapp.ui.components.SciFiBackground
import com.willydev.rickandmortyapp.ui.components.SciFiTopBar

@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    SciFiBackground {
        Scaffold(
            topBar = {
                SciFiTopBar()
            },
            containerColor = Color.Transparent,
            modifier = modifier.fillMaxSize()
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (val state = uiState) {
                    is CharacterListUiState.Loading -> {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                    is CharacterListUiState.Error -> {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Error: ${state.message}", color = MaterialTheme.colorScheme.error)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { viewModel.retry() }) {
                                Text("Retry")
                            }
                        }
                    }
                    is CharacterListUiState.Success -> {
                        CharacterList(
                            characters = state.characters,
                            isNextPageLoading = state.isNextPageLoading,
                            error = state.error,
                            onLoadMore = { viewModel.loadNextPage() },
                            onRetryMore = { viewModel.retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterList(
    characters: List<Character>,
    isNextPageLoading: Boolean,
    error: String?,
    onLoadMore: () -> Unit,
    onRetryMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false

            lastVisibleItem.index >= (listState.layoutInfo.totalItemsCount - 5)
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore && !isNextPageLoading && error == null) {
            onLoadMore()
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(characters) { character ->
            CharacterItem(
                character = character
            )
        }

        item {
            if (isNextPageLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            if (error != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = error, color = MaterialTheme.colorScheme.error)
                    TextButton(onClick = onRetryMore) {
                        Text("Retry loading more", color = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier
) {
    GlassmorphismCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SubcomposeAsyncImage(
                model = character.image,
                contentDescription = "Image of ${character.name}",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(32.dp)),
                contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                error = {
                    AsyncImage(
                        model = R.drawable.placeholder,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(
                                when (character.status.lowercase()) {
                                    "alive" -> MaterialTheme.colorScheme.primary
                                    "dead" -> MaterialTheme.colorScheme.error
                                    else -> Color.LightGray
                                }
                            )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Status: ${character.status}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = when (character.status.lowercase()) {
                            "alive" -> MaterialTheme.colorScheme.primary
                            "dead" -> MaterialTheme.colorScheme.error
                            else -> Color.LightGray
                        }
                    )
                }
            }
        }
    }
}