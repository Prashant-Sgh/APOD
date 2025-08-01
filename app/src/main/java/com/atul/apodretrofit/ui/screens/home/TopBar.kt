package com.atul.apodretrofit.ui.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
//    viewModel: HomeGridViewModel,
//    context: Context = LocalContext.current,
    isDarkTheme: StateFlow<Boolean>,
    onThemeToggle: () -> Unit
) {
//    TopAppBar(
//        title = { Text("Dark theme") },
//        actions = {
//            Switch(
//                checked = viewModel.isDark.collectAsState().value,
//                onCheckedChange = { viewModel.toggleTheme(context)  }
//            )
//        }
//    )

    val isDarkTheme = isDarkTheme.collectAsState().value

    TopAppBar(
        title = {
            Text(
                text = "Dark theme",
                color = if (isDarkTheme) Color.White else Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
                },
        actions = {

            Switch(
                checked = isDarkTheme,
                onCheckedChange = { onThemeToggle() },
                colors = SwitchDefaults.colors(checkedThumbColor = Color.White)
            )

        }
    )

}