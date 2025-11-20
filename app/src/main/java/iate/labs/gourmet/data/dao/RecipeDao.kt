package iate.labs.gourmet.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import iate.labs.gourmet.data.entity.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)

    @Query("SELECT * from recipes WHERE recipeId = :id")
    fun getRecipe(id: Int): Flow<Recipe>

    @Query("SELECT * from recipes ORDER BY name ASC")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Delete
    suspend fun delete(recipe: Recipe)
}