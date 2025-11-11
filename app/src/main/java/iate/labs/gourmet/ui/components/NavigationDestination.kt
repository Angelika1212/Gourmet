package iate.labs.gourmet.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import iate.labs.gourmet.R
import androidx.compose.ui.graphics.vector.ImageVector

interface NavigationDestination{
    val route: String
    val title: Int
    val icon: ImageVector?
}

object RecipesDestination : NavigationDestination{
    override val route = "recipe"
    override val title = R.string.recipe_all_list_title
    override val icon = Icons.Default.Menu
}

object EntryRecipeDestination : NavigationDestination{
    override val route = "recipe_entry"
    override val title = R.string.recipe_entry_title
    override val icon = Icons.Default.AddCircle
}

object HomeDestination: NavigationDestination{
    override val route = "home"
    override val title = R.string.recipe_favorite_list_title
    override val icon = Icons.Default.Star
}

object EditRecipeDestination: NavigationDestination{
    override val route = "recipe_edit"
    override val title = R.string.recipe_edit_title
    override val icon = Icons.Default.Edit
    const val itemIdArg = "recipeId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

object RecipeDetailsDestination: NavigationDestination {
    override val route = "recipe_details"
    override val title = R.string.recipe_detail_title
    override val icon = Icons.Default.Info
    const val itemIdArg = "recipeId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

