package com.atul.apodretrofit.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.atul.apodretrofit.R
import com.atul.apodretrofit.data.offline.SavedItemEntity
import com.atul.apodretrofit.ui.screens.home.HomeGridViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: HomeGridViewModel,
    selectedItem: SavedItemEntity,
    isItemSaved: Boolean,
    toggleSavedItem: (SavedItemEntity) -> Unit,
    onBack: () -> Unit,
    isDarkTheme: Boolean
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
//    var saveState by remember { mutableStateOf(isItemSaved) }

    val savedItems by viewModel.savedItems.collectAsState()
    val isSaved = savedItems.contains(selectedItem.date)

    // Define colors based on theme
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White
    val primaryTextColor = if (isDarkTheme) Color.White else Color.Black
    val secondaryTextColor = if (isDarkTheme) Color.LightGray else Color.DarkGray
    val headingColor = if (isDarkTheme) Color.Gray else Color(0xFF555555)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Detail", color = primaryTextColor) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = primaryTextColor
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            toggleSavedItem(selectedItem)
//                            saveState = !saveState
                            scope.launch {
                                snackbarHostState.showSnackbar("Toggled")
                            }
                        }
                    ) {
                        Icon(
//                            imageVector = if (saveState) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            imageVector = if (isSaved) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = primaryTextColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor
                )
            )
        },
        containerColor = backgroundColor,
    ) { paddingValues ->

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(backgroundColor)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(selectedItem.url)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Detail Image",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = selectedItem.date,
                color = secondaryTextColor,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = selectedItem.title,
                color = primaryTextColor,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "EXPLANATION--",
                color = headingColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = selectedItem.explanation,
                color = secondaryTextColor,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}





//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DetailScreen(
//    selectedItem: SavedItemEntity,
//    isItemSaved: Boolean,
//    toggleSavedItem: (SavedItemEntity) -> Unit,
//    onBack: () -> Unit,
//) {
//
//    val snackbarHostState = remember { SnackbarHostState() }
//    val scope = rememberCoroutineScope()
//
//    var saveState by remember { mutableStateOf(isItemSaved) }
//
//    Scaffold(
//        snackbarHost = { SnackbarHost(snackbarHostState) },
//        topBar = {
//            TopAppBar(
//                title = { Text("Detail") },
//                navigationIcon = {
//                    IconButton(onClick = onBack) {
//                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                },
//                actions = {
//                    IconButton(
//                        onClick = {
//                            toggleSavedItem(selectedItem)
//                            saveState = !saveState
//                            Log.d("atul", "DetailScreen: $saveState")
//                        scope.launch {
//                            snackbarHostState.showSnackbar("Toggled")
//                        }
//                    }
//                    ) {
//                        Icon(
//                            imageVector = if(saveState) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
//                            contentDescription = "Favorite"
//                        )
//                    }
//                }
//            )
//        },
//        containerColor = Color.Black,
//    ) { paddingValues ->
//
//        val scrollState = rememberScrollState()
//
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//                .fillMaxSize()
//                .verticalScroll(scrollState)
//                .background(Color.Black)
//                .padding(16.dp),
//            verticalArrangement = Arrangement.Top,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(240.dp)
//                    .clip(RoundedCornerShape(16.dp)),
//                contentAlignment = Alignment.Center
//            ) {
//                    AsyncImage(
//                        model = ImageRequest.Builder(LocalContext.current)
//                            .data(selectedItem.url)
//                            .crossfade(true)
//                            .build(),
//                        contentDescription = "Detail Image",
//                        contentScale = ContentScale.Crop,
//                        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
//                        modifier = Modifier
//                            .fillMaxSize(),
//                    )
//            }
//
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Text(
//                text = selectedItem.date,
//                color = Color.LightGray,
//                fontSize = 14.sp,
//                modifier = Modifier.align(Alignment.Start)
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            Text(
//                text = selectedItem.title,
//                color = Color.White,
//                fontSize = 22.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.align(Alignment.Start)
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            Text(
//                text = "EXPLANATION--", // heading
//                color = Color.Gray,
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Medium,
//                modifier = Modifier.align(Alignment.Start)
//            )
//
//            Spacer(modifier = Modifier.height(6.dp))
//
//            Text(
//                text = selectedItem.explanation,
//                color = Color.LightGray,
//                fontSize = 15.sp,
//                lineHeight = 20.sp,
//                modifier = Modifier.align(Alignment.Start)
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//        }
//    }
//}