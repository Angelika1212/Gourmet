package iate.labs.gourmet.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.util.TableInfo
import iate.labs.gourmet.R
import iate.labs.gourmet.R.string
import iate.labs.gourmet.data.utils.LocaleManager
import iate.labs.gourmet.data.utils.localizedStringResource
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeTopBar(
    title: String,
    searchValue: String = "",
    onSearchValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = false,
    canUseSearch: Boolean = false,
    navigateUp: () -> Unit = {}
) {
    Column (
        modifier = modifier.fillMaxWidth()
    ) {
        CenterAlignedTopAppBar(
            title = { Text(title) },
            modifier = modifier,
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = Color.White,
            ),
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = localizedStringResource(string.back_button),
                            tint = Color.White
                        )
                    }
                }
            },
            actions = {
                LanguageDropdown()
            }
        )
        if (canUseSearch) {
            SearchRecipeBar(
                searchValue = searchValue,
                onSearchValueChange = onSearchValueChange,
            )
        }
    }

}

@Composable
fun SearchRecipeBar(
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        value = searchValue,
        onValueChange = onSearchValueChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "searchBar"
            )
        },
        placeholder = {Text(stringResource(R.string.recipe_search))},
        modifier = modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.primary,
        ),
        singleLine = true,
        enabled = true
    )
}

@Composable
fun LanguageDropdown(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = localizedStringResource(R.string.select_language),
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(localizedStringResource(R.string.language_ru))
                    }
                },
                onClick = {
                    LocaleManager.setLocale(context, Locale("ru"))
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(localizedStringResource(R.string.language_en))
                    }
                },
                onClick = {
                    LocaleManager.setLocale(context, Locale("en"))
                    expanded = false
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewRecipeTopBar(){
    RecipeTopBar("Recipes", searchValue = "", onSearchValueChange = {}, canNavigateBack = false, canUseSearch = true)
}