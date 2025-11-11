package iate.labs.gourmet.ui.screens

import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import iate.labs.gourmet.model.RecipeEditViewModel
import iate.labs.gourmet.ui.AppViewModelProvider
import iate.labs.gourmet.ui.components.EditRecipeDestination
import iate.labs.gourmet.ui.components.RecipeTopBar
import kotlinx.coroutines.launch

@Composable
fun RecipeEditScreen (
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipeEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            RecipeTopBar(
                title = stringResource( EditRecipeDestination.title),
                canNavigateBack = true,
                navigateUp = onNavigateUp
            )
        },
        modifier = modifier
    ) {
        innerPadding ->
        RecipeEntryBody(
            recipeUiState = viewModel.recipeUiState,
            onRecipeValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateRecipe()
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
        )
    }
}