package com.atul.apodretrofit

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.atul.apodretrofit.viewmodel.ApodViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest

class ApodScreen (){
@SuppressLint("NotConstructor")
@Composable
fun ApodScreen(viewModel: ApodViewModel = viewModel()) {
    val items = viewModel.apodItems
    val error = viewModel.hasError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (error) {
            Text(
                text = "There was an error",
                color = Color.Red,
                style = MaterialTheme.typography.bodyLarge
            )
        } else if (items != null) {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalPlatformContext.current)
                            .data(if (items[0].media_type == "image") items[0].url else items[0].hdurl)
                            .listener(onError = { request, result ->
                                Log.e("CoilError", "Failed to load image: ${result.throwable.message}")
                                // You can inspect result.throwable for more details
                            })
                            .build(),
                        placeholder = painterResource(R.drawable.ic_launcher_foreground),
                        error = painterResource(R.drawable.ic_launcher_foreground),
                        contentDescription = items[0].title ?: "NASA Astronomy Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .background(color = Color.Red) // Keep this for now to see if placeholder shows
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop,
                    )

            Text(
                text = items[0].title ,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text =  items[0].url + "           " + items[0].hdurl + "           " + items[0].explanation,
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            CircularProgressIndicator()
        }
    }
}
}