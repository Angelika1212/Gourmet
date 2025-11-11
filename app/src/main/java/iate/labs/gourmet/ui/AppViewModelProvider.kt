package iate.labs.gourmet.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import iate.labs.gourmet.RecipeApplication
import iate.labs.gourmet.model.HomeViewModel
import iate.labs.gourmet.model.RecipeDetailsViewModel
import iate.labs.gourmet.model.RecipeEditViewModel
import iate.labs.gourmet.model.RecipeEntryViewModel
import iate.labs.gourmet.model.RecipesViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            RecipesViewModel(
                recipeApplication().container.recipesRepository
            )
        }


        initializer {
            HomeViewModel(recipeApplication().container.recipesRepository)
        }

        initializer {
            RecipeEditViewModel(
                this.createSavedStateHandle(),
                recipeApplication().container.recipesRepository
            )
        }

        initializer {
            RecipeEntryViewModel(recipeApplication().container.recipesRepository)
        }

        initializer {
            RecipeDetailsViewModel(
                this.createSavedStateHandle(),
                recipeApplication().container.recipesRepository
            )
        }
    }
}

fun CreationExtras.recipeApplication(): RecipeApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as RecipeApplication)