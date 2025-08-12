package com.atul.apodretrofit.ui.screens.offlines

//import coil.request.crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import com.atul.apodretrofit.ui.screens.home.HomeGridViewModel


@Composable
fun OfflineDetailScreen(
    viewModel: HomeGridViewModel,
    onBack: () -> Unit,
    isDarkTheme: Boolean // new parameter
) {
    val selectedItem by viewModel.offlineSelectedItem.collectAsState()
    val currentItem = remember { selectedItem }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // Theme colors
    val backgroundColor = if (isDarkTheme) Color.Black else Color.White
    val textPrimaryColor = if (isDarkTheme) Color.White else Color.Black
    val textSecondaryColor = if (isDarkTheme) Color.LightGray else Color.DarkGray
    val explanationHeaderColor = if (isDarkTheme) Color(0xFFFFA726) else Color(0xFFEF6C00) // orange shade
    val iconTint = if (isDarkTheme) Color.White else Color.Black

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .verticalScroll(scrollState)
                .padding(20.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = iconTint
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = selectedItem?.date ?: "",
                color = textSecondaryColor,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = selectedItem?.title ?: "",
                color = textPrimaryColor,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(selectedItem?.url)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Detail Image",
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "EXPLANATION",
                color = explanationHeaderColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = selectedItem?.explanation ?: "",
                color = textSecondaryColor,
                fontSize = 15.sp,
                lineHeight = 22.sp
            )
        }

        // Snackbar Host
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}







//@Composable
//fun OfflineDetailScreen(
//    viewModel: HomeGridViewModel,
//    onBack: () -> Unit
//) {
//    val selectedItem by viewModel.offlineSelectedItem.collectAsState()
////    var isSaved by remember { mutableStateOf(true) }
//    val currentItem = remember { selectedItem }
//
//    val snackbarHostState = remember { SnackbarHostState() }
//    val scope = rememberCoroutineScope()
//    val scrollState = rememberScrollState()
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.Black)
//                .verticalScroll(scrollState)
//                .padding(20.dp)
//        ) {
//            IconButton(
//                onClick = onBack,
//                modifier = Modifier.size(32.dp)
//            ) {
//                Icon(
//                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                    contentDescription = "Back",
//                    tint = Color.White
//                )
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Text(
//                text = selectedItem?.date ?: "",
//                color = Color.Gray,
//                fontSize = 14.sp
//            )
//
//            Spacer(modifier = Modifier.height(4.dp))
//
//            Text(
//                text = selectedItem?.title ?: "",
//                color = Color.White,
//                fontSize = 26.sp,
//                fontWeight = FontWeight.Bold,
//                lineHeight = 32.sp
//            )
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(240.dp)
//                    .clip(RoundedCornerShape(16.dp))
//            ) {
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(selectedItem?.url)
//                        .crossfade(true)
//                        .build(),
//                    contentDescription = "Detail Image",
//                    contentScale = ContentScale.Crop,
//                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
//                    modifier = Modifier.fillMaxSize()
//                )
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Text(
//                text = "EXPLANATION",
//                color = Color(0xFFFFA726), // orange
//                fontSize = 14.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            Spacer(modifier = Modifier.height(10.dp))
//
//            Text(
//                text = selectedItem?.explanation ?: "",
//                color = Color.LightGray,
//                fontSize = 15.sp,
//                lineHeight = 22.sp
//            )
//        }
//
//        // Floating Heart Icon
////        IconButton(
////            onClick = {
////
////                val toggleItem = SavedItemEntity(selectedItem?.date ?: "", selectedItem?.explanation ?: "", selectedItem?.hdurl ?: "", selectedItem?.media_type ?: "", selectedItem?.title ?: "", selectedItem?.url ?: "")
//////                selectedItem?.let { viewModel.toggleSavedItem(currentItem ?: toggleItem)}
////                currentItem?.let { viewModel.toggleSavedItem(it)}
////                scope.launch {
////                    snackbarHostState.showSnackbar("Toggled")
////                }
////                isSaved = !isSaved
////            },
////            modifier = Modifier
////                .align(Alignment.BottomEnd)
////                .padding(24.dp)
////                .background(Color.Black.copy(alpha = 0.6f), shape = CircleShape).navigationBarsPadding()
////        ) {
////            Icon(
////                imageVector = if (isSaved) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
////                contentDescription = "Toggle Favorite",
////                tint = Color.White
////            )
////        }
//
//        // Snackbar Host
//        SnackbarHost(
//            hostState = snackbarHostState,
//            modifier = Modifier.align(Alignment.BottomCenter)
//        )
//    }
//}