package iate.labs.gourmet.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import iate.labs.gourmet.model.RecipeDetails
import iate.labs.gourmet.model.RecipeEntryViewModel
import iate.labs.gourmet.model.RecipeUiState
import iate.labs.gourmet.ui.AppViewModelProvider
import iate.labs.gourmet.ui.components.RecipeTopBar
import iate.labs.gourmet.R
import iate.labs.gourmet.ui.theme.GourmetTheme
import kotlinx.coroutines.launch

@Composable
fun RecipeEntryScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: RecipeEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold (
        topBar = {
            RecipeTopBar(
                title = "Создать рецепт",
                canNavigateBack = canNavigateBack,
                navigateUp = onNavigateUp
            )
        }
    ) { innerPadding -> RecipeEntryBody(
        recipeUiState = viewModel.recipeUiState,
        onRecipeValueChange = viewModel::updateUiState,
        onSaveClick = {
            coroutineScope.launch {
                viewModel.saveRecipe()
                navigateBack()
            }
        },
        modifier = Modifier
            .padding(
                start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                top = innerPadding.calculateTopPadding(),
                end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
            )
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
    )
    }
}

@Composable
fun RecipeEntryBody(
    recipeUiState: RecipeUiState,
    onRecipeValueChange: (RecipeDetails) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        RecipeInputForm(
            recipeDetails = recipeUiState.recipeDetails,
            onValueChange = onRecipeValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            enabled = recipeUiState.isEntryValid,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonColors(
                containerColor = Color.Green,
                contentColor = Color.White,
                disabledContentColor = Color.Gray,
                disabledContainerColor = Color.LightGray)
        ) {
            Text(text = stringResource(R.string.save_action))
        }

    }

}

@Composable
fun RecipeInputForm(
    recipeDetails: RecipeDetails,
    modifier: Modifier,
    onValueChange: (RecipeDetails) -> Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        RecipeInputName(recipeDetails = recipeDetails, modifier = modifier, onValueChange = onValueChange, enabled = enabled)
        RecipeInputDescription(recipeDetails = recipeDetails, modifier = modifier, onValueChange = onValueChange, enabled = enabled)
        RecipeInputIngredients(recipeDetails = recipeDetails, modifier = modifier, onValueChange = onValueChange, enabled = enabled)
        RecipeInputProcess(recipeDetails = recipeDetails, modifier = modifier, onValueChange = onValueChange, enabled = enabled)
        RecipeInputCookingTime(recipeDetails = recipeDetails, modifier = modifier, onValueChange = onValueChange, enabled = enabled)
    }
}

@Composable
fun RecipeInputName(
    recipeDetails: RecipeDetails,
    modifier: Modifier,
    onValueChange: (RecipeDetails) -> Unit = {},
    enabled: Boolean = true
){
    OutlinedTextField(
        value = recipeDetails.name,
        onValueChange = { onValueChange(recipeDetails.copy(name = it)) },
        label = { Text(stringResource(R.string.recipe_name_req)) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        singleLine = true
    )
}

@Composable
fun RecipeInputDescription(
    recipeDetails: RecipeDetails,
    modifier: Modifier,
    onValueChange: (RecipeDetails) -> Unit = {},
    enabled: Boolean = true
){
    OutlinedTextField(
        value = recipeDetails.description,
        onValueChange = { onValueChange(recipeDetails.copy(description = it)) },
        label = { Text(stringResource(R.string.recipe_description)) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        singleLine = true
    )
}

@Composable
fun RecipeInputIngredients(
    recipeDetails: RecipeDetails,
    modifier: Modifier,
    onValueChange: (RecipeDetails) -> Unit = {},
    enabled: Boolean = true
){
    OutlinedTextField(
        value = recipeDetails.ingredients,
        onValueChange = { onValueChange(recipeDetails.copy(ingredients = it)) },
        label = { Text(stringResource(R.string.recipe_ingredients)) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        singleLine = true
    )
}

@Composable
fun RecipeInputProcess(
    recipeDetails: RecipeDetails,
    modifier: Modifier,
    onValueChange: (RecipeDetails) -> Unit = {},
    enabled: Boolean = true
){
    OutlinedTextField(
        value = recipeDetails.recipeProcess,
        onValueChange = { onValueChange(recipeDetails.copy(recipeProcess = it)) },
        label = { Text(stringResource(R.string.recipe_process)) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        singleLine = true
    )
}

@Composable
fun RecipeInputCookingTime(
    recipeDetails: RecipeDetails,
    modifier: Modifier,
    onValueChange: (RecipeDetails) -> Unit = {},
    enabled: Boolean = true
){
    OutlinedTextField(
        value = recipeDetails.cookingTime,
        onValueChange = { onValueChange(recipeDetails.copy(cookingTime = it)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        label = { Text(stringResource(R.string.recipe_cooking_time)) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        singleLine = true
    )
}

@Preview (showBackground = true)
@Composable
fun RecipeEntryBodyPreview(){
    GourmetTheme {
        RecipeEntryBody(recipeUiState = RecipeUiState(
            RecipeDetails(
                name = "ПРАГА",
                cookingTime = "30",
                description = "Шоколадный торт с коньяком",
                ingredients = "Мука, сахар...",
                recipeProcess = "Время готовить"
            )
        ), onRecipeValueChange = {}, onSaveClick = {})
    }
}