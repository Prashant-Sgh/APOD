package com.atul.apodretrofit.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.atul.apodretrofit.data.offline.SavedItemEntity
import com.atul.apodretrofit.model.APODapiItem

@Composable
fun HomeScreen(
    uiState: State<HomeGridUiState>,
    onToggleSaved: (SavedItemEntity) -> Unit,
    onItemClick: (APODapiItem) -> Unit,
    isDarkTheme: State<Boolean>,
    onShowMore: () -> Unit
) {
    val isDarkTheme = isDarkTheme.value
    val uiState by uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkTheme) Color(0xFF0E172A) else Color.White)
            .padding(horizontal = 16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier.weight(1f).padding(vertical = 15.dp)
        ) {
            items(uiState.items) { item ->
                ApodCard(
                    item = item,
                    isDarkTheme = isDarkTheme,
                    onToggleSaved = onToggleSaved,
                    onItemClick = onItemClick
                )
            }

            item(span = { GridItemSpan(2) }) {
                Button(
                    onClick = {
                        onShowMore()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isDarkTheme) Color(0xFF172A46) else Color(0xFFE2E8F0),
                        contentColor = if (isDarkTheme) Color.White else Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Show More",
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(vertical = 1.dp)
                    )
                }
            }

        }

//        BottomNavigationBar(isDarkTheme)
    }
}

@Composable
fun ApodCard(
    item: APODapiItem,
    isDarkTheme: Boolean,
    onToggleSaved: (SavedItemEntity) -> Unit,
    onItemClick: (APODapiItem) -> Unit
) {
    val bgColor = if (isDarkTheme) Color(0xFF172A46) else Color(0xFFF1F5F9)
    val textColor = if (isDarkTheme) Color.White else Color.Black

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .clickable { onItemClick(item) }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = item.url,
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.title,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = textColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = {
                    val toggleItem = SavedItemEntity(item.date, item.explanation, item.hdurl, item.media_type, item.title, item.url)
                    onToggleSaved(toggleItem)
                }) {
                    Icon(
//                        imageVector = if (item.isSaved) Icons.Default.Star else Icons.Default.StarBorder,
                        imageVector = Icons.Outlined.Star,
                        contentDescription = "Save",
                        tint = Color.White
                    )
                }
            }

            item.date?.let {
                Text(
                    text = it,
                    fontSize = 12.sp,
                    color = textColor.copy(alpha = 0.7f)
                )
            }
        }
    }
}


//@Composable
//fun BottomNavigationBar(isDark: Boolean) {
//    BottomAppBar(
//        containerColor = if (isDark) Color(0xFFDCDCDC) else Color(0xFF0F172A),
//        contentColor = if(isDark) Color.White else Color(0xFFDCDCDC),
//        actions = {
//                IconButton(onClick = { /* TODO */ }) {
//                    Icon(Icons.Default.Home, contentDescription = "Home")
//                }
//                Spacer(modifier = Modifier.weight(1f))
//                IconButton(onClick = { /* TODO */ }) {
//                    Icon(Icons.Default.Star, contentDescription = "Favorites")
//                }
//        }
//    )
//}






//@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
//@Composable
//fun HomeGridScreen(
//    navController: NavHostController,
//    viewModel: HomeGridViewModel,
//    paddingValues: PaddingValues,
//    context: Context = LocalContext.current
//) {
//    val state by viewModel.uiState
//
//    if (state.isLoading) {
//        // Show shimmer while loading
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xFF21214A)),
//            contentPadding = PaddingValues(12.dp),
//            verticalArrangement = Arrangement.spacedBy(12.dp),
//            horizontalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            items(6) {
//                ShimmerCard()
//            }
//        }
//    } else if (state.error != null) {
//        Text("Error: ${state.error}")
//    } else {
//        val snackbarHostState = remember { SnackbarHostState() }
//        val scope = rememberCoroutineScope()
//
//        Scaffold(
//            snackbarHost = { SnackbarHost(snackbarHostState) },
//        ) { paddingValues ->
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(2),
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(paddingValues)
//                    .background(Color(0xFF001C34)),
//                contentPadding = PaddingValues(12.dp),
//                verticalArrangement = Arrangement.spacedBy(12.dp),
//                horizontalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                items(state.items, key = { it.date }) { item ->
//
////                    if (item.url != null && item.url?.contains(".png") == false) {
//                        GridItemCard(item = item, onClick = {
//                            viewModel.selectItem(item)
//                            navController.navigate(NavRoutes.Detail)
//                        }, isSaved = false, onClickSave = {
//                            viewModel.toggleSavedItem(item)
//                            scope.launch {
//                                snackbarHostState.showSnackbar("Toggled")
//                            }
//                        })
////                    }
//
//                }
//                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
//                    Button(
//                        onClick = {
//                            viewModel.updatePastDays(viewModel.pastDays + 4)
//                            viewModel.loadGridItems(viewModel.pastDays)
////                            viewModel.loadMoreItems()
//                        }, // or whatever your logic is
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 8.dp)
//                    ) {
//                        Text("Show More")
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//@Composable
//fun GridItemCard(item: APODapiItem, onClick: () -> Unit, isSaved: Boolean, onClickSave: () -> Unit) {
//    val transition = remember { MutableTransitionState(false) }
//    transition.targetState = true
//
//    AnimatedVisibility(
//        visibleState = transition,
//        enter = fadeIn(animationSpec = tween(500)) + scaleIn(),
//        exit = fadeOut()
//    ) {
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .aspectRatio(0.75f),
////                .clickable { onClick() },
//            elevation = CardDefaults.cardElevation(6.dp),
//            shape = RoundedCornerShape(12.dp)
//        ) {
//            Box (
//                modifier = Modifier
//                    .background(Color.LightGray.copy(alpha = 0.3f))
//                    .padding(4.dp)
//                    .fillMaxSize()
//            )
//            {
//                Column(
//                    modifier = Modifier
//                        .background(Color.LightGray.copy(alpha = 0.3f))
//                        .padding(4.dp)
//                        .clickable { onClick() }
//                        .fillMaxSize(),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//
//                        AsyncImage(
//                            model = ImageRequest.Builder(LocalContext.current)
//                                .data(item.url)
//                                .crossfade(true)
//                                .build(),
//                            contentDescription = item.title,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .weight(1f)
//                                .clip(RoundedCornerShape(10.dp)),
//                            placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
//                            contentScale = ContentScale.Crop,
//                        )
//
//                    Spacer(modifier = Modifier.height(4.dp))
//
//                    Text(
//                        text = item.title,
//                        style = MaterialTheme.typography.labelMedium,
//                        textAlign = TextAlign.Center,
//                        maxLines = 2,
//                        overflow = TextOverflow.Ellipsis,
//                        modifier = Modifier.padding(horizontal = 8.dp)
//                    )
//                }
//                Icon(
//                    imageVector = if (isSaved) Icons.Filled.Star else Icons.Outlined.Star,
//                    contentDescription = "Save icon",
//                    modifier = Modifier
//                        .size(30.dp)
//                        .clickable { onClickSave() }
//                )
//            }
//        }
//    }
//}
//@Composable
//fun ShimmerCard() {
//    val shimmerColors = listOf(
//        Color.LightGray.copy(alpha = 0.6f),
//        Color.LightGray.copy(alpha = 0.2f),
//        Color.LightGray.copy(alpha = 0.6f)
//    )
//    val transition = rememberInfiniteTransition(label = "shimmer")
//    val translateAnim = transition.animateFloat(
//        initialValue = 0f,
//        targetValue = 1000f,
//        animationSpec = infiniteRepeatable(
//            animation = tween(1000, easing = LinearEasing)
//        ), label = "shimmerAnim"
//    )
//
//    val brush = Brush.linearGradient(
//        colors = shimmerColors,
//        start = Offset.Zero,
//        end = Offset(x = translateAnim.value, y = translateAnim.value)
//    )
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .aspectRatio(0.75f),
//        shape = RoundedCornerShape(12.dp),
//        elevation = CardDefaults.cardElevation(6.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(brush)
//        ) {}
//    }
//}