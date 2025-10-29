package iate.labs.gourmet.data.repository

import iate.labs.gourmet.data.entity.ItemRecipe
import kotlinx.coroutines.flow.Flow

interface ItemRecipeRepository {
    fun getAllItemsStream(): Flow<List<ItemRecipe>>

    fun getItemStream(id: Int): Flow<ItemRecipe?>

    suspend fun insertItem(item: ItemRecipe)

    suspend fun updateItem(item: ItemRecipe)
}