package com.atul.apodretrofit.ui.screens.home

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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.atul.apodretrofit.model.APODapiItem
import com.atul.apodretrofit.model.GridItem
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.atul.apodretrofit.R
import com.atul.apodretrofit.navigation.NavRoutes
import com.atul.apodretrofit.ui.screens.detail.DetailScreenViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun HomeGridScreen(
//    navController: NavHostController,
//    viewModel: HomeGridViewModel
//) {
//    val state by viewModel.uiState
//
//    if (state.isLoading) {
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            CircularProgressIndicator()
//        }
//    } else if (state.error != null) {
//        Text("Error: ${state.error}")
//    } else {
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(3),
//            modifier = Modifier.fillMaxSize(),
//            contentPadding = PaddingValues(8.dp),
//            verticalArrangement = Arrangement.spacedBy(8.dp),
//            horizontalArrangement = Arrangement.spacedBy(8.dp)
//        ) {
//            items(state.items) { item ->
////                GridItemCard(item = item, onClick = { onItemClick(item) })
//                GridItemCard(item = item, onClick = {
////                    detailScreenViewModel.selectItem(item)
//                    viewModel.selectItem(item)
//                    navController.navigate(NavRoutes.Detail)
//                })
//            }
//        }
//    }
//}
//
//@Composable
////fun GridItemCard(item: GridItem, onClick: () -> Unit) {
//fun GridItemCard(item: APODapiItem, onClick: () -> Unit) {
//    Card(
//        modifier = Modifier
//            .aspectRatio(1f)
//            .fillMaxWidth()
//            .clickable { onClick() },
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current).data(item.url).crossfade(true).build(),
//                contentDescription = item.title,
//                modifier = Modifier.fillMaxSize(),
//                placeholder = painterResource(id = R.drawable.ic_launcher_foreground),)
//        }
//    }
//}

//**********************************************************//

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeGridScreen(
    navController: NavHostController,
    viewModel: HomeGridViewModel
) {
    val state by viewModel.uiState

    if (state.isLoading) {
        // Show shimmer while loading
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8)),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(6) {
                ShimmerCard()
            }
        }
    } else if (state.error != null) {
        Text("Error: ${state.error}")
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8F8F8)),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.items) { item ->
                GridItemCard(item = item, onClick = {
                    viewModel.selectItem(item)
                    navController.navigate(NavRoutes.Detail)
                })
            }
        }
    }
}

@Composable
fun GridItemCard(item: APODapiItem, onClick: () -> Unit) {
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
                .aspectRatio(0.75f)
                .clickable { onClick() },
            elevation = CardDefaults.cardElevation(6.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.LightGray.copy(alpha = 0.3f))
                    .padding(4.dp)
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
        }
    }
}

@Composable
fun ShimmerCard() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing)
        ), label = "shimmerAnim"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.75f),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush)
        ) {}
    }
}