package iate.labs.gourmet.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home

import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null
) {
    data object Recipe : Screen(
        route = "recipe",
        title = "Рецепты",
        icon = Icons.Default.Home
    )

    data object CreateRecipe : Screen(
        route = "createRecipe",
        title = "Добавить рецепт",
        icon = Icons.Default.AddCircle
    )

    data object Profile : Screen(
        route = "profile",
        title = "Профиль",
        icon = Icons.Default.AccountCircle
    )
}