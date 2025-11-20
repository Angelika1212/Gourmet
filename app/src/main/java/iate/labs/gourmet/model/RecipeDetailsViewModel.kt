package iate.labs.gourmet.model

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iate.labs.gourmet.data.repository.RecipeRepository
import iate.labs.gourmet.ui.components.RecipeDetailsDestination
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class RecipeDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val recipeId: Int = checkNotNull(savedStateHandle[RecipeDetailsDestination.itemIdArg])

    val uiState: StateFlow<RecipeDetailsUiState> =
        recipeRepository.getRecipeStream(recipeId)
            .filterNotNull()
            .map {
                RecipeDetailsUiState(recipeDetails = it.toRecipeDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = RecipeDetailsUiState()
            )

    suspend fun deleteRecipe(){
        recipeRepository.deleteRecipe(uiState.value.recipeDetails.toRecipe())
    }

    fun likeRecipe(){
        viewModelScope.launch {
            val currentState = uiState.value.recipeDetails
            val updatedRecipe = currentState.copy(isLiked = !currentState.isLiked)
            recipeRepository.updateRecipe(updatedRecipe.toRecipe())
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class RecipeDetailsUiState(
    val recipeDetails: RecipeDetails = RecipeDetails()
)