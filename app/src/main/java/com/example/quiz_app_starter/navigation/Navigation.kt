package com.example.quiz_app_starter.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.quiz_app_starter.presentation.FinishScreen
import com.example.quiz_app_starter.presentation.MainMenuScreen
import com.example.quiz_app_starter.presentation.question.QuestionScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.MainMenuScreen.route
    ) {
        composable(route = Screen.MainMenuScreen.route) {
            MainMenuScreen(
                onPlayClick = {
                    navController.navigate(Screen.QuestionScreen.route)
                }
            )
        }
        
        composable(route = Screen.QuestionScreen.route) {
            QuestionScreen(
                onFinish = { points ->
                    // Navigate to FinishScreen with points argument
                    navController.navigate(Screen.FinishScreen.createRoute(points)) {
                        // Ensure back from FinishScreen goes to MainMenu
                        popUpTo(Screen.MainMenuScreen.route) {
                            inclusive = false
                        }
                    }
                }
            )
        }
        
        composable(
            route = Screen.FinishScreen.route,
            arguments = listOf(
                navArgument("points") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val points = backStackEntry.arguments?.getInt("points") ?: 0
            FinishScreen(
                points = points,
                onRefresh = {
                    // Restart quiz: go to QuestionScreen and clear FinishScreen from stack
                    navController.navigate(Screen.QuestionScreen.route) {
                        popUpTo(Screen.MainMenuScreen.route) {
                            inclusive = false
                        }
                    }
                },
                onHome = {
                    // Go to MainMenu and clear everything
                    navController.navigate(Screen.MainMenuScreen.route) {
                        popUpTo(Screen.MainMenuScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
