package com.atul.apodretrofit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.atul.apodretrofit.model.DataStoreManager
import com.atul.apodretrofit.navigation.AppNavHost
import com.atul.apodretrofit.navigation.NavRoutes
import com.atul.apodretrofit.ui.components.BottomBar
import com.atul.apodretrofit.ui.screens.home.HomeGridViewModel
import com.atul.apodretrofit.ui.screens.home.HomeGridViewModelFactory
import com.atul.apodretrofit.ui.screens.home.TopBar
import com.atul.apodretrofit.ui.theme.APODretrofitTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataStoreManager = DataStoreManager(this)
        val homeGridFactory = HomeGridViewModelFactory(dataStoreManager, this)
        val homeGridViewModel = ViewModelProvider(this, homeGridFactory)[HomeGridViewModel::class.java]

        setContent {
            val darkMode by homeGridViewModel.isDark.collectAsState()
            val navController = rememberNavController()
            val currentDestinationRoute = navController.currentBackStackEntryAsState().value?.destination?.route

            APODretrofitTheme(darkTheme = darkMode) {
                Scaffold(
                    topBar = { if(currentDestinationRoute !in listOf(NavRoutes.Detail, NavRoutes.Offline))  {
                        TopBar(homeGridViewModel)
                    }
                             },
                    bottomBar = { if(currentDestinationRoute !in listOf(NavRoutes.Detail)) {
                        BottomBar(navController)
                        } }
                ) {
                    Box(modifier = Modifier.padding(it))
                    {
                        AppNavHost(navController, homeGridViewModel)
                    }
                }
            }
        }
    }
}


//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
////                    Greeting(
////                        name = "Android",
////                        modifier = Modifier.padding(innerPadding)
////                    )
//                    Box(
//                        modifier = Modifier.padding(innerPadding)
//                    ){
//                        ApodScreen().ApodScreen()
//                    }
//                }
//        }
//    }
//}

//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}

//
//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//private fun PreviewThis() {
////    ApodScreen()
//}