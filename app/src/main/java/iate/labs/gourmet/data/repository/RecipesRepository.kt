package iate.labs.gourmet.data.repository

import iate.labs.gourmet.data.dao.RecipeDao
import iate.labs.gourmet.data.entity.Recipe
import kotlinx.coroutines.flow.Flow

class RecipesRepository(private val recipeDao: RecipeDao) : RecipeRepository {
    override fun getAllRecipesStream(): Flow<List<Recipe>> = recipeDao.getAllRecipes()

    override fun getRecipeStream(id: Int): Flow<Recipe?> = recipeDao.getRecipe(id)

    override suspend fun insertRecipe(recipe: Recipe) = recipeDao.insert(recipe)

    override suspend fun deleteRecipe(recipe: Recipe) = recipeDao.delete(recipe)

    override suspend fun updateRecipe(recipe: Recipe) = recipeDao.update(recipe)
}