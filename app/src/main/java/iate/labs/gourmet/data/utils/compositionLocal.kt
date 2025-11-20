package iate.labs.gourmet.data.utils

import androidx.compose.runtime.staticCompositionLocalOf
import java.util.Locale

val LocalAppLocale = staticCompositionLocalOf { Locale.getDefault() }