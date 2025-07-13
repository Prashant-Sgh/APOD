package com.atul.apodretrofit.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.atul.apodretrofit.model.GridItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeGridScreen(
    viewModel: HomeGridViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onItemClick: (GridItem) -> Unit
) {
    val state by viewModel.uiState

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (state.error != null) {
        Text("Error: ${state.error}")
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.items) { item ->
                GridItemCard(item = item, onClick = { onItemClick(item) })
            }
        }
    }
}

@Composable
fun GridItemCard(item: GridItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = item.title, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
