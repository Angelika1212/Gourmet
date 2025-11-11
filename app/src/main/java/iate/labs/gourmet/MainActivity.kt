package iate.labs.gourmet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import iate.labs.gourmet.ui.components.EditRecipeDestination
import iate.labs.gourmet.ui.components.EntryRecipeDestination
import iate.labs.gourmet.ui.components.HomeDestination
import iate.labs.gourmet.ui.components.RecipeBottomNavBar
import iate.labs.gourmet.ui.components.NavigationDestination
import iate.labs.gourmet.ui.components.RecipeDetailsDestination
import iate.labs.gourmet.ui.components.RecipesDestination
import iate.labs.gourmet.ui.screens.HomeScreen
import iate.labs.gourmet.ui.screens.RecipeDetailsScreen
import iate.labs.gourmet.ui.screens.RecipeEditScreen
import iate.labs.gourmet.ui.screens.RecipeEntryScreen
import iate.labs.gourmet.ui.screens.RecipesScreen
import iate.labs.gourmet.ui.theme.GourmetTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ){
                GourmetTheme {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            RecipeBottomNavBar(navController)
                        }
                    ) { innerPadding ->
                        NavHost(
                            navController = navController,
                            startDestination = RecipesDestination.route,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            composable(RecipesDestination.route) {
                                RecipesScreen(
                                    navController,
                                    { navController.navigate(EntryRecipeDestination.route) },
                                    { navController.navigate("${RecipeDetailsDestination.route}/${it}")} )
                            }

                            composable(EntryRecipeDestination.route) {
                                RecipeEntryScreen(
                                    navigateBack = {navController.popBackStack()},
                                    onNavigateUp = {navController.navigateUp()})
                            }

                            composable(HomeDestination.route) {
                                HomeScreen(
                                    { navController.navigate(EntryRecipeDestination.route) },
                                    { navController.navigate("${RecipeDetailsDestination.route}/${it}")})
                            }

                            composable(
                                route = EditRecipeDestination.routeWithArgs,
                                arguments = listOf(navArgument(EditRecipeDestination.itemIdArg) {
                                    type = NavType.IntType
                                })
                            ) {
                                RecipeEditScreen(
                                    navigateBack = { navController.popBackStack() },
                                    onNavigateUp = { navController.navigateUp() })
                            }

                            composable(
                                route = RecipeDetailsDestination.routeWithArgs,
                                arguments = listOf(navArgument(RecipeDetailsDestination.itemIdArg) {
                                    type = NavType.IntType
                                })
                            ) {
                                RecipeDetailsScreen(
                                    navigateToEditRecipe = { navController.navigate("${EditRecipeDestination.route}/${it}") },
                                    navigateBack = { navController.navigateUp() })
                            }
                        }
                    }
                }

            }
        }
    }
}
