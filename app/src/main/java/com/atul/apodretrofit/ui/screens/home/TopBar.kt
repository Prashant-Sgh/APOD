package com.atul.apodretrofit.ui.screens.home

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    viewModel: HomeGridViewModel,
    context: Context = LocalContext.current
) {
    TopAppBar(
        title = { Text("APOD Screen") },
        actions = {
            Switch(
                checked = viewModel.isDark.collectAsState().value,
                onCheckedChange = { viewModel.toggleTheme(context)  }
            )
        }
    )
}