package iate.labs.gourmet.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iate.labs.gourmet.data.repository.RecipeRepository
import iate.labs.gourmet.ui.components.EditRecipeDestination
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RecipeEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    var recipeUiState by mutableStateOf(RecipeUiState())
        private set
    private val recipeId: Int = checkNotNull(savedStateHandle[EditRecipeDestination.itemIdArg])

    init {
        viewModelScope.launch {
            recipeUiState = recipeRepository.getRecipeStream(recipeId)
                .filterNotNull()
                .first()
                .toRecipeUiState(true)
        }
    }

    suspend fun updateRecipe() {
        if (validateInput(recipeUiState.recipeDetails)) {
            recipeRepository.updateRecipe(recipeUiState.recipeDetails.toRecipe())
        }
    }

    fun updateUiState(recipeDetails: RecipeDetails) {
        recipeUiState =
            RecipeUiState(
                recipeDetails = recipeDetails,
                isEntryValid = validateInput(recipeDetails))
    }

    private fun validateInput(uiState: RecipeDetails = recipeUiState.recipeDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && description.isNotBlank() &&
                    ingredients.isNotBlank() && recipeProcess.isNotBlank() &&
                    cookingTime.toInt() > 0
        }
    }
}