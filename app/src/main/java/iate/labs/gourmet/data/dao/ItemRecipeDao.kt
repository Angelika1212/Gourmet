package iate.labs.gourmet.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import iate.labs.gourmet.data.entity.ItemRecipe
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemRecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ItemRecipe)

    @Update
    suspend fun update(item: ItemRecipe)

    @Query("SELECT * from recipes WHERE recipeId = :id")
    fun getItem(id: Int): Flow<ItemRecipe>

    @Query("SELECT * from recipes ORDER BY name ASC")
    fun getAllItems(): Flow<List<ItemRecipe>>
}