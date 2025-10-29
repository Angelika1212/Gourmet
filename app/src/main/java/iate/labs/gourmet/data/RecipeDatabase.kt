package iate.labs.gourmet.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import iate.labs.gourmet.data.dao.ItemRecipeDao
import iate.labs.gourmet.data.entity.ItemRecipe

@Database(entities = [ItemRecipe::class], version = 1, exportSchema = false)
abstract class RecipeDatabase: RoomDatabase() {
    abstract fun itemDao(): ItemRecipeDao

    companion object {
        @Volatile
        private var Instance: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, RecipeDatabase::class.java, "item_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}