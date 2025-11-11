package iate.labs.gourmet.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import iate.labs.gourmet.data.entity.Recipe
import iate.labs.gourmet.data.repository.RecipeRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RecipesViewModel(recipeRepository: RecipeRepository) : ViewModel() {
    val recipeUiState: StateFlow<RecipesUiState> =
        recipeRepository.getAllRecipesStream().map { RecipesUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = RecipesUiState()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class RecipesUiState(val recipeList: List<Recipe> = listOf())