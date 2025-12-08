package iate.labs.gourmet.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.navigation.currentBackStackEntryAsState
import iate.labs.gourmet.data.utils.localizedStringResource

val bottomNavItems = listOf(
    RecipesDestination,
    HomeDestination
)

@Composable
fun RecipeBottomNavBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary
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
                        Icon(it, contentDescription = localizedStringResource(screen.title), tint = Color.White)
                    }
                },
                label = { localizedStringResource(screen.title)?.let { Text(it, color = Color.White) } }
            )
        }
    }
}

@Preview
@Composable
fun BottomNavBarPreview(){
    RecipeBottomNavBar(navController = rememberNavController())
}