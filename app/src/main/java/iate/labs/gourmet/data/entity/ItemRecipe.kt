package iate.labs.gourmet.data.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class ItemRecipe(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipeId")
    val id: Int = 0,
    val name: String,
    val cookingTime: Int,
    val ingredients: String,
    val recipeProcess: String,
    val userId: Int
)