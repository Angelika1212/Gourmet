package iate.labs.gourmet.data.repository

import iate.labs.gourmet.data.dao.ItemRecipeDao
import iate.labs.gourmet.data.entity.ItemRecipe
import kotlinx.coroutines.flow.Flow

class RecipesRepository(private val itemDao: ItemRecipeDao) : ItemRecipeRepository {
    override fun getAllItemsStream(): Flow<List<ItemRecipe>> = itemDao.getAllItems()

    override fun getItemStream(id: Int): Flow<ItemRecipe?> = itemDao.getItem(id)

    override suspend fun insertItem(item: ItemRecipe) = itemDao.insert(item)

    override suspend fun updateItem(item: ItemRecipe) = itemDao.update(item)
}