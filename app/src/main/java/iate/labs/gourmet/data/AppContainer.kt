package iate.labs.gourmet.data

import android.content.Context
import iate.labs.gourmet.data.repository.ItemRecipeRepository
import iate.labs.gourmet.data.repository.RecipesRepository

interface AppContainer {
    val itemsRepository: ItemRecipeRepository
}


class AppDataContainer(private val context: Context) : AppContainer {
    override val itemsRepository: ItemRecipeRepository by lazy {
        RecipesRepository(RecipeDatabase.getDatabase(context).itemDao())
    }
}