package iate.labs.gourmet.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import iate.labs.gourmet.R
import iate.labs.gourmet.data.entity.Recipe
import iate.labs.gourmet.model.RecipesViewModel
import iate.labs.gourmet.ui.AppViewModelProvider
import iate.labs.gourmet.ui.components.RecipeTopBar
import iate.labs.gourmet.ui.theme.Beige
import iate.labs.gourmet.ui.theme.GourmetTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(
    navController: NavController,
    navigateToRecipeEntry: () -> Unit,
    navigateToRecipeUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipesViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.recipeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { RecipeTopBar( title = stringResource(R.string.recipe_all_list_title)) },
        floatingActionButton = { RecipeEntryAction(navigateToRecipeEntry)}
    ) {
        innerPadding ->
        RecipeBody(
            recipeList = homeUiState.recipeList,
            onRecipeClick = navigateToRecipeUpdate,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
    }
}

@Composable
fun RecipeEntryAction(navigateToRecipeEntry: () -> Unit) {
    FloatingActionButton(
        onClick = navigateToRecipeEntry,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(LocalLayoutDirection.current)
            )
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.recipe_entry_title))
    }
}

@Composable
fun RecipeBody(
    recipeList: List<Recipe>,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (recipeList.isEmpty()) {
            Text(
                text = stringResource(R.string.no_recipe_list),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            RecipeList(
                recipeList = recipeList,
                onItemClick = { onRecipeClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }

    }
}

@Composable
private fun RecipeList(
    recipeList: List<Recipe>,
    onItemClick: (Recipe) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = recipeList, key = { it.id }) { recipe ->
            RecipeItem(recipe = recipe,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onItemClick(recipe) })
        }
    }

}

@Composable
fun RecipeItem(
    recipe: Recipe,
    modifier: Modifier,
    onClick: () -> Unit = {}
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Beige)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            RecipeImage(modifier = modifier.weight(1f))
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
            ) {
                RecipeHeader(recipe = recipe, onClick = onClick)
                RecipeDescription(recipe = recipe)
                RecipeCookingTime(recipe = recipe)
            }
        }

    }
}

@Composable
fun RecipeImage(
    recipe: Recipe? = null,
    modifier: Modifier = Modifier
){
    if (recipe == null) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.image_icon),
            contentDescription = stringResource(R.string.no_image_description),
            modifier = Modifier.scale(1.5f),
            contentScale = ContentScale.Crop
        )
    }

}

@Composable
fun RecipeHeader(
    recipe: Recipe,
    onClick: () -> Unit = {}
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = recipe.name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.weight(1f))

        LikedButton(isLiked = recipe.isLiked, onClick = onClick)
    }
}

@Composable
fun RecipeCookingTime(
    recipe: Recipe
){
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.recipe_cookingTime),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.weight(1f))

        Text(
            text = "${recipe.cookingTime} мин",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun RecipeDescription(
    recipe: Recipe
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = recipe.description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun LikedButton(
    isLiked: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
){
    //переделать кнопку на обычную иконку
    val imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder

    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = "IsLiked",
            tint = if (isLiked) Color.Red else Color.Black
        )
    }

}

@Preview(showBackground = true)
@Composable
fun RecipeBodyPreview() {
    GourmetTheme {
        RecipeBody(
            recipeList = listOf(
                Recipe(
                    id = 0,
                    name = "ПРАГА",
                    cookingTime = 30,
                    description = "Шоколадный торт",
                    ingredients = "Коньяк, какао, яйца, сгущенка ...0",
                    recipeProcess = "Cook",
                    isLiked = false
                ),
                Recipe(
                    id = 1,
                    name = "ЛИМОННЫЙ КЕКС",
                    cookingTime = 10,
                    description = "Любимый лимонный кекс",
                    ingredients = "Лимон, мука, яйца, соль, сахар",
                    recipeProcess = "Cook",
                    isLiked = true
                )
            ),
            onRecipeClick = {},
            modifier = Modifier
        )
    }
}