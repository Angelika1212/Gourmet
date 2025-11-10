package iate.labs.gourmet.data.repository

import iate.labs.gourmet.data.entity.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun getAllRecipesStream(): Flow<List<Recipe>>

    fun getRecipesStream(id: Int): Flow<Recipe?>

    suspend fun insertRecipe(recipe: Recipe)
    suspend fun deleteRecipe(recipe: Recipe)
    suspend fun updateRecipe(recipe: Recipe)
}