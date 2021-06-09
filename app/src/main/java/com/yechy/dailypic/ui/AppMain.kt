package com.yechy.dailypic.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yechy.dailypic.ui.home.MainViewModel
import com.yechy.dailypic.ui.theme.AppTheme

@Composable
fun AppMain() {
    
    AppTheme {

        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) {
                val viewModel = hiltViewModel<MainViewModel>(backStackEntry = it)
                MainPage(mainViewModel = viewModel)
            }

            composable(Screen.Gallery.route) {

            }
        }
        
    }

}

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object Gallery : Screen("Gallery")
}