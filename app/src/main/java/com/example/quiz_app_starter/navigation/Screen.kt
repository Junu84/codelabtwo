package com.example.quiz_app_starter.navigation

sealed class Screen(val route: String) {
    object MainMenuScreen : Screen("main_menu")
    object QuestionScreen : Screen("question")
    object FinishScreen : Screen("finish/{points}") {
        fun createRoute(points: Int) = "finish/$points"
    }
}
