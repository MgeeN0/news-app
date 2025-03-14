package fi.oamk.news_app.ui.appbars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import fi.oamk.news_app.viewmodel.ArticlesSearchViewModel
import fi.oamk.news_app.viewmodel.CategoryViewModel
import fi.oamk.news_app.viewmodel.SearchOptionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopNewsBar(navController: NavController,searchViewModel: SearchOptionsViewModel = viewModel()) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    TopAppBar(
        title = { Text(searchViewModel.searchBar) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        actions = {
            IconButton(onClick = { navController.navigate("settings") }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Open submenu"
                )
            }
        }
    )
}