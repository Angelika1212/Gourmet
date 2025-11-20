package iate.labs.gourmet.data.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import java.util.Locale

object LocaleManager {
    private const val PREFS_NAME = "app_locale_prefs"
    private const val KEY_LOCALE = "key_locale"

    var currentLocale by mutableStateOf(Locale.getDefault())

    fun init(context: Context) {
        currentLocale = getSavedLocale(context)
    }

    fun setLocale(context: Context, locale: Locale) {
        currentLocale = locale
        saveLocale(context, locale)
    }

    private fun saveLocale(context: Context, locale: Locale) {
        val prefs: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LOCALE, locale.language).apply()
    }

    private fun getSavedLocale(context: Context): Locale {
        val prefs: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val lang = prefs.getString(KEY_LOCALE, Locale.getDefault().language)
        return Locale(lang!!)
    }
}