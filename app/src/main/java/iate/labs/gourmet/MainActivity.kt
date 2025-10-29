package iate.labs.gourmet

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import iate.labs.gourmet.ui.components.RecipeBottomNavBar
import iate.labs.gourmet.ui.components.Screen
import iate.labs.gourmet.ui.screens.CreateRecipeScreen
import iate.labs.gourmet.ui.screens.ProfileScreen
import iate.labs.gourmet.ui.screens.RecipeScreen
import iate.labs.gourmet.ui.theme.GourmetTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GourmetTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        RecipeBottomNavBar(navController)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Recipe.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Recipe.route) { RecipeScreen(navController) }
                        composable(Screen.CreateRecipe.route) { CreateRecipeScreen(navController) }
                        composable(Screen.Profile.route) { ProfileScreen(navController) }
                    }
                }
            }
        }
    }
}
