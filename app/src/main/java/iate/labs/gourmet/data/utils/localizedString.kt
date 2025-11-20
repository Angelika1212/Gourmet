package iate.labs.gourmet.data.utils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@SuppressLint("LocalContextConfigurationRead")
@Composable
fun localizedStringResource(resId: Int, vararg args: Any): String {
    val context = LocalContext.current
    val locale = LocaleManager.currentLocale
    val config = context.resources.configuration
    config.setLocale(locale)
    val localizedContext = context.createConfigurationContext(config)
    return localizedContext.resources.getString(resId, *args)
}