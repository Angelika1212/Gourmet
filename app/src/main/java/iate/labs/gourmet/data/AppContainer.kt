package iate.labs.gourmet.data

import android.content.Context
import iate.labs.gourmet.data.repository.RecipeRepository
import iate.labs.gourmet.data.repository.RecipesRepository

interface AppContainer {
    val recipesRepository: RecipeRepository
}


class AppDataContainer(private val context: Context) : AppContainer {
    override val recipesRepository: RecipeRepository by lazy {
        RecipesRepository(RecipeDatabase.getDatabase(context).recipeDao())
    }
}