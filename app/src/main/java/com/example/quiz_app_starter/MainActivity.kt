package com.example.quiz_app_starter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.quiz_app_starter.navigation.AppNavigation
import com.example.quiz_app_starter.ui.theme.QuizappstarterTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QuizappstarterTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("MainActivity", "onStart called")

    }

    override fun onResume() {
        super.onResume()
        Log.i("MainActivity", "onResume called")

    }

    override fun onPause() {
        super.onPause()
        Log.i("MainActivity", "onPause called")

    }

    override fun onStop() {
        super.onStop()
        Log.i("MainActivity", "onStop called")

    }


}
