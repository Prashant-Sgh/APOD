package com.atul.apodretrofit.ui.screens.offlines

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.atul.apodretrofit.R
import com.atul.apodretrofit.data.offline.SavedItemEntity
import com.atul.apodretrofit.model.APODapiItem
import com.atul.apodretrofit.navigation.NavRoutes
import com.atul.apodretrofit.ui.screens.home.HomeGridViewModel
import com.atul.apodretrofit.ui.screens.home.TopBar
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SavedItemsScreen(
    viewModel: HomeGridViewModel,
    navController: NavHostController
) {

    val items by viewModel.containAllSavedItems.collectAsState()

    Scaffold(
        topBar = {
                TopAppBar (title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Saved Items")
                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "Delete all",
                            modifier = Modifier.clickable {
                                viewModel.deleteAllItems()
                            }
                        )
                    }
                })

        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 120.dp),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items.size) { index ->
                OfflineGridItemCard(item = items[index], onClick = {
                    viewModel.offlineSelectedItem(items[index])
                    navController.navigate(NavRoutes.Detail)
                }, isSaved = true, onClickSave = { })
            }
        }
    }
}


@Composable
fun OfflineGridItemCard(item: SavedItemEntity, onClick: () -> Unit, isSaved: Boolean, onClickSave: () -> Unit) {
    val transition = remember { MutableTransitionState(false) }
    transition.targetState = true

    AnimatedVisibility(
        visibleState = transition,
        enter = fadeIn(animationSpec = tween(500)) + scaleIn(),
        exit = fadeOut()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.75f),
//                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Box (
                modifier = Modifier
                    .background(Color.LightGray.copy(alpha = 0.3f))
                    .padding(4.dp)
                    .fillMaxSize()
            )
            {
                Column(
                    modifier = Modifier
                        .background(Color.LightGray.copy(alpha = 0.3f))
                        .padding(4.dp)
                        .clickable { onClick() }
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.url)
                            .crossfade(true)
                            .build(),
                        contentDescription = item.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp)),
                        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelMedium,
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
                Icon(
                    imageVector = if (isSaved) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "Save icon",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { onClickSave() }
                )
            }
        }
    }
}











@Composable
fun SavedItemCard(item: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f), // Makes it square
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = item, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

