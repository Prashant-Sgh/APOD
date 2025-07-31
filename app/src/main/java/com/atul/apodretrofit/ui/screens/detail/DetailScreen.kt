package com.atul.apodretrofit.ui.screens.detail

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.atul.apodretrofit.R
import com.atul.apodretrofit.data.offline.SavedItemEntity
import com.atul.apodretrofit.model.APODapiItem
import com.atul.apodretrofit.ui.screens.home.HomeGridViewModel
import kotlinx.coroutines.launch

//@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
 viewModel: HomeGridViewModel,
    onBack: () -> Unit
) {

    val selectedItem by viewModel.selectedItem.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val item = APODapiItem("copyright", "date", "explanation", "hdurl", "media_type", "service_version", "title", "url")
                        Log.d("ToggleDebug", "Toggling save for item: ${selectedItem?.date}")
                            viewModel.toggleSavedItem(selectedItem?: item)
                        scope.launch {
                            snackbarHostState.showSnackbar("Toggled")
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite"
                        )
                    }
                }
            )
        },
        containerColor = Color.Black,
    ) { paddingValues ->

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color.Black)
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
                            .data(selectedItem?.url)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Detail Image",
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                        modifier = Modifier
                            .fillMaxSize(),
                    )
            }


            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = selectedItem?.date ?: "",
                color = Color.LightGray,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = selectedItem?.title ?: "",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "EXPLANATION--", // heading
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = selectedItem?.explanation ?: "",
                color = Color.LightGray,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "HDURL--", // heading
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = selectedItem?.hdurl ?: "",
                color = Color.LightGray,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "URL--", // heading
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = selectedItem?.url ?: "",
                color = Color.LightGray,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}