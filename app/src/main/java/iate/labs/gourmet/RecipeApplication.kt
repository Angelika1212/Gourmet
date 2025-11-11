package iate.labs.gourmet

import android.app.Application
import iate.labs.gourmet.data.AppContainer
import iate.labs.gourmet.data.AppDataContainer

class RecipeApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}