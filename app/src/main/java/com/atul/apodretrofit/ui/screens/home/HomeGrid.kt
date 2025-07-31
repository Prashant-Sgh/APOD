package com.atul.apodretrofit.ui.screens.home

import android.content.Context
import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
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
import androidx.compose.ui.tooling.preview.Preview
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
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeGridScreen(
    navController: NavHostController,
    viewModel: HomeGridViewModel,
    paddingValues: PaddingValues,
    context: Context = LocalContext.current
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
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF8F8F8)),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(state.items, key = { it.date }) { item ->

                    if (item.url != null && item.url?.contains(".png") == false) {
                        GridItemCard(item = item, onClick = {
                            viewModel.selectItem(item)
                            navController.navigate(NavRoutes.Detail)
                        }, isSaved = false, onClickSave = {
                            viewModel.toggleSavedItem(item)
                            scope.launch {
                                snackbarHostState.showSnackbar("Toggled")
                            }
                        })
                    }

                }
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    Button(
                        onClick = {
                            viewModel.updatePastDays(viewModel.pastDays + 4)
                            viewModel.loadGridItems(viewModel.pastDays)
//                            viewModel.loadMoreItems()
                        }, // or whatever your logic is
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text("Show More")
                    }
                }
            }
        }
    }
}


@Composable
fun GridItemCard(item: APODapiItem, onClick: () -> Unit, isSaved: Boolean, onClickSave: () -> Unit) {
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
                            contentScale = ContentScale.Crop,
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