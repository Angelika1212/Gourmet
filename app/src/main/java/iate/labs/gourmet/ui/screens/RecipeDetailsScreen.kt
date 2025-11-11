package iate.labs.gourmet.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import iate.labs.gourmet.model.RecipeDetailsViewModel
import iate.labs.gourmet.ui.AppViewModelProvider
import iate.labs.gourmet.ui.components.RecipeTopBar
import iate.labs.gourmet.R
import iate.labs.gourmet.data.entity.Recipe
import iate.labs.gourmet.model.RecipeDetails
import iate.labs.gourmet.model.RecipeDetailsUiState
import iate.labs.gourmet.model.toRecipe
import iate.labs.gourmet.ui.theme.GourmetTheme
import kotlinx.coroutines.launch


@Composable
fun RecipeDetailsScreen(
    navigateToEditRecipe: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            RecipeTopBar(
                title = uiState.value.recipeDetails.name,
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            RecipeEditButton(
                id = uiState.value.recipeDetails.id,
                navigateToEditRecipe = navigateToEditRecipe,
                modifier = modifier)
        },
        modifier = modifier,
    ){
        innerPadding ->
        RecipeDetailsBody(
            recipeDetailsUiState = uiState.value,
            onDelete = {
                coroutineScope.launch {
                    viewModel.deleteRecipe()
                    navigateBack()
                }
            },
            onToggleLike = {
                viewModel.likeRecipe()
            },
            modifier = Modifier
                .padding(
                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                )
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
fun RecipeEditButton(
    id: Int,
    navigateToEditRecipe: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    FloatingActionButton(
        onClick = { navigateToEditRecipe(id) },
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .padding(
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(LocalLayoutDirection.current)
            )
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = stringResource(R.string.edit_item_title),
        )
    }
}

@Composable
private fun RecipeDetailsBody(
    recipeDetailsUiState: RecipeDetailsUiState,
    onDelete: () -> Unit,
    onToggleLike: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
        RecipeDetails(
            recipe = recipeDetailsUiState.recipeDetails.toRecipe(),
            onToggleLike = onToggleLike,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.delete))
        }
        if (deleteConfirmationRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}

@Composable
fun RecipeDetails(
    recipe: Recipe,
    onToggleLike: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier, colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ) {
            Row (
                modifier = modifier,
                horizontalArrangement = Arrangement.End
            ) {
                LikeButton(
                    isLiked = recipe.isLiked,
                    onToggleLike = onToggleLike
                )
            }

            Row (
                modifier = modifier,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.image_icon),
                    contentDescription = "No image",
                    modifier = Modifier.scale(1.5f),
                    contentScale = ContentScale.Crop
                )

            }

            RecipeDetailsRow(
                labelResID = R.string.recipe,
                recipeDetail = recipe.name,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen.padding_medium
                    )
                )
            )

            HorizontalDivider(thickness = 2.dp, color = Color.White)

            RecipeDetailsRow(
                labelResID = R.string.description,
                recipeDetail = recipe.description,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen.padding_medium
                    )
                )
            )

            HorizontalDivider(thickness = 2.dp, color = Color.White)

            RecipeDetailsRow(
                labelResID = R.string.cookingTime,
                recipeDetail = recipe.cookingTime.toString(),
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen.padding_medium
                    )
                )
            )

            HorizontalDivider(thickness = 2.dp, color = Color.White)

            RecipeDetailsRow(
                labelResID = R.string.ingredient,
                recipeDetail = recipe.ingredients,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen.padding_medium
                    )
                )
            )

            HorizontalDivider(thickness = 2.dp, color = Color.White)

            RecipeDetailsRow(
                labelResID = R.string.process,
                recipeDetail = recipe.recipeProcess,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen.padding_medium
                    )
                )
            )
        }
    }
}

@Composable
fun RecipeDetailsRow(
    @StringRes labelResID: Int,
    recipeDetail: String,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier) {
        Text(text = stringResource(labelResID), fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))

    }

    Row (modifier = modifier) {
        Text(text = recipeDetail, fontWeight = FontWeight.Light)
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { },
        title = { Text(stringResource(R.string.attention)) },
        text = { Text(stringResource(R.string.delete_question)) },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.no))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.yes))
            }
        })
}

@Composable
fun LikeButton(
    isLiked: Boolean,
    onToggleLike: () -> Unit
){
    var currentState by remember {mutableStateOf(isLiked)}

    IconButton(
        onClick = {
            currentState = !currentState
            onToggleLike()
        }
    ) {
        Icon (
            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = if (isLiked) "Добавить в избранное" else "Удалить из избранного",
            tint = if (isLiked) Color.Red else Color.Black
        )
    }
}


@Preview
@Composable
fun PreviewRecipeDetailScreen(){
    GourmetTheme {
        RecipeDetailsBody(
            RecipeDetailsUiState(
                RecipeDetails(
                    id = 0,
                    name ="Торт \"ПРАГА\"",
                    ingredients = "Яйца, мука, сахар, разрыхлитель, молоко, коньяк....",
                    cookingTime = "20",
                    description = "Вкусный шоколадный торт с коньяком",
                    recipeProcess = "Смешать яйца с сахаром до мягких пиков, добавить муку и какао...",
                    isLiked = true
                    )
            ),
            onDelete = {},
            onToggleLike = {}
        )
    }
}