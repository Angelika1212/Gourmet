package iate.labs.gourmet.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import iate.labs.gourmet.model.HomeViewModel
import iate.labs.gourmet.ui.AppViewModelProvider
import iate.labs.gourmet.ui.components.HomeDestination
import iate.labs.gourmet.ui.components.RecipeTopBar
import iate.labs.gourmet.R
import iate.labs.gourmet.data.entity.Recipe
import iate.labs.gourmet.data.utils.LocaleManager
import iate.labs.gourmet.data.utils.localizedStringResource
import iate.labs.gourmet.ui.theme.GourmetTheme
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToRecipeEntry: () -> Unit,
    navigateToRecipeUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val favoriteRecipesList: List<Recipe> = homeUiState.recipeList.filter{ it.isLiked }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RecipeTopBar(
                title = localizedStringResource(HomeDestination.title),
                canNavigateBack = false
            )
        },

        floatingActionButton = {
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
                    contentDescription = localizedStringResource(R.string.recipe_entry_title)
                )
            }
        },
    ) { innerPadding ->
        HomeBody(
            recipeList = favoriteRecipesList,
            onRecipeClick = navigateToRecipeUpdate,
            modifier = modifier.fillMaxSize(),
            contentPadding = innerPadding,
        )
    }
}


@Composable
private fun HomeBody(
    recipeList: List<Recipe>,
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        if (recipeList.isEmpty()) {
            Text(
                text = localizedStringResource(R.string.no_favorite_recipe),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(contentPadding),
            )
        } else {
            RecipeList(
                recipeList = recipeList,
                onRecipeClick = { onRecipeClick(it.id) },
                contentPadding = contentPadding,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small))
            )
        }
    }
}


@Composable
private fun RecipeList(
    recipeList: List<Recipe>,
    onRecipeClick: (Recipe) -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding
    ) {
        items(items = recipeList, key = { it.id }) { recipe ->
           RecipeItem(recipe = recipe,
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small))
                    .clickable { onRecipeClick(recipe) })
        }
    }
}





@Preview
@Composable
fun HomePreviewScreen(){
    GourmetTheme {
        RecipeBody(
            recipeList = listOf(
                Recipe(
                    id = 0,
                    name = "ПРАГА",
                    cookingTime = 30,
                    description = "Шоколадный торт",
                    ingredients = "Коньяк, какао, яйца, сгущенка ...0",
                    category = "Десерт",
                    recipeProcess = "Cook",
                    isLiked = true
                ),
                Recipe(
                    id = 1,
                    name = "ЛИМОННЫЙ КЕКС",
                    cookingTime = 10,
                    description = "Любимый лимонный кекс",
                    ingredients = "Лимон, мука, яйца, соль, сахар",
                    recipeProcess = "Cook",
                    category = "Десерт",
                    isLiked = true
                )
            ),
            onRecipeClick = {},
            modifier = Modifier
        )
    }
}