package iate.labs.gourmet.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.wear.compose.navigation.currentBackStackEntryAsState

val bottomNavItems = listOf(
    Screen.Recipe,
    Screen.CreateRecipe,
    Screen.Profile
)

@Composable
fun RecipeBottomNavBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    BottomAppBar(
        containerColor = Color.Red
    ) {
        bottomNavItems.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    screen.icon?.let {
                        Icon(it, contentDescription = screen.title, tint = Color.White)
                    }
                },
                label = { screen.title?.let { Text(it, color = Color.White) } }
            )
        }
    }
}