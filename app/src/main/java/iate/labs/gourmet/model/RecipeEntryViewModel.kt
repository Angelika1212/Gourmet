package iate.labs.gourmet.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import iate.labs.gourmet.data.entity.Recipe
import iate.labs.gourmet.data.repository.RecipeRepository

class RecipeEntryViewModel(
    private val recipeRepository: RecipeRepository
): ViewModel() {
    var recipeUiState by mutableStateOf(RecipeUiState())
        private set

    fun updateUiState(recipeDetails: RecipeDetails) {
        recipeUiState =
            RecipeUiState(recipeDetails = recipeDetails, isEntryValid = validateInput(recipeDetails))
    }

    private fun validateInput(uiState: RecipeDetails = recipeUiState.recipeDetails): Boolean {
        return with(uiState) {
            name.isNotBlank() && cookingTime.toIntOrNull()!! > 0 &&
                    ingredients.isNotBlank() && category.isNotBlank() &&
                    description.isNotBlank() && recipeProcess.isNotBlank()
        }
    }

    suspend fun saveRecipe() {
        if (validateInput()) {
            recipeRepository.insertRecipe(recipeUiState.recipeDetails.toRecipe())
        }
    }
}

data class RecipeUiState(
    val recipeDetails: RecipeDetails = RecipeDetails(),
    val isEntryValid: Boolean = false
)

data class RecipeDetails(
    val id: Int = 0,
    val name: String = "",
    val cookingTime: String = "",
    val description: String = "",
    val category: String = "",
    val ingredients: String = "",
    val recipeProcess: String = "",
    val isLiked: Boolean = false
)

fun RecipeDetails.toRecipe(): Recipe = Recipe(
    id = id,
    name = name,
    cookingTime = cookingTime.toIntOrNull() ?: 0,
    ingredients = ingredients,
    description = description,
    category = category,
    recipeProcess = recipeProcess,
    isLiked = isLiked
)

fun Recipe.toRecipeUiState(isEntryValid: Boolean = false): RecipeUiState = RecipeUiState (
    recipeDetails = this.toRecipeDetails(),
    isEntryValid = isEntryValid
)

fun Recipe.toRecipeDetails(): RecipeDetails = RecipeDetails(
    id = id,
    name = name,
    cookingTime = cookingTime.toString(),
    ingredients = ingredients,
    description = description,
    category = category,
    recipeProcess = recipeProcess,
    isLiked = isLiked
)