package iate.labs.gourmet.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import iate.labs.gourmet.data.dao.RecipeDao
import iate.labs.gourmet.data.entity.Recipe

@Database(
    entities = [Recipe::class],
    version = 2,
    exportSchema = false)

abstract class RecipeDatabase: RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var Instance: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, RecipeDatabase::class.java, "recipe_database")
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}