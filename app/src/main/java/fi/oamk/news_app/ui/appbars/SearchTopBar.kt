package fi.oamk.news_app.ui.appbars

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import fi.oamk.news_app.viewmodel.ArticlesSearchViewModel
import fi.oamk.news_app.viewmodel.CategoryViewModel
import fi.oamk.news_app.viewmodel.SearchOptionsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopNewsBar(searchViewModel: SearchOptionsViewModel = viewModel()) {
    TopAppBar(
        title = { Text(searchViewModel.searchBar) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}