package com.atul.apodretrofit.ui.screens.detail

import android.annotation.SuppressLint
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.atul.apodretrofit.model.APODapiItem
import com.atul.apodretrofit.ui.screens.home.HomeGridViewModel

//@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
 viewModel: HomeGridViewModel,
    onBack: () -> Unit
) {

    val selectedItem by viewModel.selectedItem.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle favorite click */ }) {
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
        val lottieComposition by rememberLottieComposition(LottieCompositionSpec.Asset("image_error.json"))
        val lottieProgress by animateLottieCompositionAsState(lottieComposition)

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
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(selectedItem?.url)
//                    .crossfade(true)
//                    .build(),
//                contentDescription = "Detail Image",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(240.dp)
//                    .clip(RoundedCornerShape(12.dp)),
//                contentScale = ContentScale.Crop,
//                onError = {
//                    // Show Lottie animation fallback
//                    LottieAnimation(
//                        composition = lottieComposition,
//                        progress = { lottieProgress },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(240.dp)
//                    )
//                }
//            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                var isError by remember { mutableStateOf(false) }

                if (!isError) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(selectedItem?.url)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Detail Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize(),
                        onError = {
                            isError = true
                        }
                    )
                }

                if (isError) {
                    LottieAnimation(
                        composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.astronaut)).value,
                        iterations = LottieConstants.IterateForever,
                        modifier = Modifier.size(150.dp)
                    )
                }
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
        }
    }
}






//@Composable
//fun DetailScreen(viewModel: HomeGridViewModel) {
//    val selectedItem by viewModel.selectedItem.collectAsState()
//
//    val isItemSaved by viewModel.isSelectedItemSaved.collectAsState()
//
//    // Safe area using Scaffold or PaddingValues
////    Scaffold { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
//                .padding(16.dp) // General padding
//        ) {
////             Image from URL using Coil 3
//            AsyncImage(
//                model = selectedItem?.url,
//                contentDescription = "Detail Image",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(240.dp)
//                    .clip(RoundedCornerShape(8.dp)),
//                contentScale = ContentScale.Crop
//            )
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // Date Text - Small
//            Text(
//                text = selectedItem?.date ?: "No data",
//                style = MaterialTheme.typography.labelSmall,
//                color = Color.Gray,
//                modifier = Modifier.padding(bottom = 4.dp)
//            )
//
//            Row(
//                modifier = Modifier.fillMaxWidth().background(Color(0xFFFFF9C4)).padding(8.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ){
//                Text(
//                    text = selectedItem?.title ?: "No data",
//                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
//                    modifier = Modifier
//                        .padding(8.dp)
//
//                )
//
//                Icon(
//                    imageVector = if (isItemSaved) (Icons.Default.Favorite) else (Icons.Default.FavoriteBorder),
//                tint = if (isItemSaved) Color.Red else Color.Black,
//                    contentDescription = if (isItemSaved) "Saved" else "Not Saved",
//                    modifier = Modifier.size(30.dp)
//                )
//            }
//
//            Spacer(modifier = Modifier.height(12.dp))
//
//            // Scrollable Explanation Text Area
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .heightIn(min = 200.dp, max = 500.dp)
//                    .border(1.dp, Color.LightGray, RoundedCornerShape(6.dp))
//                    .padding(8.dp)
//                    .verticalScroll(rememberScrollState())
//            ) {
//                Text(
//                    text = ("EXPLANATION:--" +selectedItem?.explanation + "MEDIA TYPE:--" + selectedItem?.media_type + "URL:--" + selectedItem?.url + "HD-URL:--" + selectedItem?.hdurl) ?: "No data",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = Color.DarkGray
//                )
//            }
//        }
//}