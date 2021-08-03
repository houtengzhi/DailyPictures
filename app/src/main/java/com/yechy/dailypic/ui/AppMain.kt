package com.yechy.dailypic.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.yechy.dailypic.ui.gallery.GalleryViewModel
import com.yechy.dailypic.ui.home.MainViewModel
import com.yechy.dailypic.ui.theme.AppTheme

@Composable
fun AppMain() {
    
    AppTheme {

        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Screen.Home.route) {
            composable(Screen.Home.route) {
                val viewModel = hiltViewModel<MainViewModel>(backStackEntry = it)
                MainPage(mainViewModel = viewModel,
                    navigateToGallery = {
                        navController.navigate("${Screen.Gallery.route}/$it")})
            }

            composable(route = "${Screen.Gallery.route}/{sourceId}",
                arguments = listOf(navArgument("sourceId") {type = NavType.IntType})
            ) {
                val viewModel = hiltViewModel<GalleryViewModel>(backStackEntry = it)
                val sourceId = it.arguments!!.getInt("sourceId")
                viewModel.getPicturesList(sourceId)
                GalleryPage(viewModel, {navController.popBackStack()})
            }
        }
        
    }

}

sealed class Screen(val route: String) {
    object Home : Screen("Home")
    object Gallery : Screen("Gallery")
}