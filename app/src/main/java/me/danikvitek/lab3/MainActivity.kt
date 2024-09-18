package me.danikvitek.lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable
import me.danikvitek.lab3.screen.StartScreen
import me.danikvitek.lab3.screen.StudentsInfo
import me.danikvitek.lab3.ui.theme.Lab3Theme

@Serializable
private data object StartScreen

@Serializable
private data object StudentsInfo

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Lab3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = StartScreen,
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        composable<StartScreen> {
                            StartScreen(
                                onNavigateToStudentInfo = { navController.navigate(route = StudentsInfo) },
                            )
                        }
                        composable<StudentsInfo> { StudentsInfo() }
                    }
                }
            }
        }
    }
}
