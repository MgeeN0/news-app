package fi.oamk.news_app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import fi.oamk.news_app.model.Article
import fi.oamk.news_app.ui.appbars.SearchTopNewsBar
import fi.oamk.news_app.ui.appbars.TopNewsBar
import fi.oamk.news_app.viewmodel.ArticleSearchUiState
import fi.oamk.news_app.viewmodel.ArticlesSearchViewModel
import fi.oamk.news_app.viewmodel.ArticlesViewModel
import fi.oamk.news_app.viewmodel.NewsUiState
import fi.oamk.news_app.viewmodel.SearchOptionsViewModel

@Composable
fun Personalized(modifier: Modifier,articlesViewModel: ArticlesSearchViewModel = viewModel(),searchOptionsViewModel: SearchOptionsViewModel = viewModel()) {
    //articlesViewModel.getArticlesList(searchOptionsViewModel.searchPhrase,searchOptionsViewModel.selectedSorting,searchOptionsViewModel.language)
    Scaffold (
        topBar = {
            SearchTopNewsBar()
        }
    )
    { innerPadding ->
        SearchArticlesScreen(Modifier.padding(innerPadding),articlesViewModel.articleUiState)
    }
    Column(
        modifier = Modifier.padding(top=120.dp,start=8.dp, end = 8.dp)
    ) {
        Row {
            OutlinedTextField(
                modifier = Modifier,
                value = searchOptionsViewModel.searchPhrase,
                onValueChange = { searchOptionsViewModel.searchPhrase = it },
                label = { Text(text = "Enter search phrase") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Button(
                modifier = Modifier.padding(top = 12.dp, start = 12.dp),
                onClick = { },
                content = { Text("Search")}
            )
        }
        Row {
            Column (
                modifier = Modifier.padding(start = 110.dp, top = 8.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .clickable { searchOptionsViewModel.setExpanded() },
                    shape = CircleShape,
                    color = Color.Magenta,
                    shadowElevation = 8.dp
                ) {
                    Text("Sort by", modifier = Modifier.padding(10.dp))
                }
                DropdownMenu(
                    expanded = searchOptionsViewModel.expanded,
                    onDismissRequest = { searchOptionsViewModel.setFalse() }
                ) {
                    DropdownMenuItem(
                        text = { Text("Relevancy") },
                        onClick = { searchOptionsViewModel.selectSorting("relevancy") }
                    )
                    DropdownMenuItem(
                        text = { Text("Popularity") },
                        onClick = { searchOptionsViewModel.selectSorting("popularity") }
                    )
                    DropdownMenuItem(
                        text = { Text("Upload date") },
                        onClick = { searchOptionsViewModel.selectSorting("publishedAt") }
                    )
                }
            }
            Column {
                Surface(
                    modifier = Modifier.padding(start=8.dp,top = 8.dp)
                        .clickable { searchOptionsViewModel.setLExpanded() },
                    shape = CircleShape,
                    color = Color.Magenta,
                    shadowElevation = 8.dp
                ) {
                    Text("Language", modifier = Modifier.padding(10.dp))
                }
                DropdownMenu(
                    expanded = searchOptionsViewModel.languageExpanded,
                    onDismissRequest = { searchOptionsViewModel.setLFalse() }
                ) {
                    DropdownMenuItem(
                        text = { Text("German") },
                        onClick = { searchOptionsViewModel.selectLanguage("de") }
                    )
                    DropdownMenuItem(
                        text = { Text("English") },
                        onClick = { searchOptionsViewModel.selectLanguage("en") }
                    )
                    DropdownMenuItem(
                        text = { Text("Spanish") },
                        onClick = { searchOptionsViewModel.selectLanguage("es") }
                    )
                    DropdownMenuItem(
                        text = { Text("French") },
                        onClick = { searchOptionsViewModel.selectLanguage("fr") }
                    )
                    DropdownMenuItem(
                        text = { Text("Italian") },
                        onClick = { searchOptionsViewModel.selectLanguage("fr") }
                    )
                    DropdownMenuItem(
                        text = { Text("Dutch") },
                        onClick = { searchOptionsViewModel.selectLanguage("nl") }
                    )
                    DropdownMenuItem(
                        text = { Text("Norwegian") },
                        onClick = { searchOptionsViewModel.selectLanguage("no") }
                    )
                    DropdownMenuItem(
                        text = { Text("Portuguese") },
                        onClick = { searchOptionsViewModel.selectLanguage("pt") }
                    )
                    DropdownMenuItem(
                        text = { Text("Russian") },
                        onClick = { searchOptionsViewModel.selectLanguage("ru") }
                    )
                    DropdownMenuItem(
                        text = { Text("Swedish") },
                        onClick = { searchOptionsViewModel.selectLanguage("sv") }
                    )
                    DropdownMenuItem(
                        text = { Text("Chinese") },
                        onClick = { searchOptionsViewModel.selectLanguage("zh") }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchArticlesScreen(modifier: Modifier,uiState: ArticleSearchUiState) {
    when (uiState) {
        is ArticleSearchUiState.NoRequest -> EmptyScreen()
        is ArticleSearchUiState.Success -> SearchNewsCards(modifier,uiState.articles)
        is ArticleSearchUiState.Error -> SearchErrorScreen()
    }
}

@Composable
fun EmptyScreen() {
    Text(
        modifier = Modifier.padding(vertical = 250.dp, horizontal = 10.dp),
        text = "articles will appear here..."
    )
}

@Composable
fun SearchNewsCards(modifier: Modifier,response: Article) {
    Text("test")
}

@Composable
fun SearchErrorScreen() {
    Text("Error retrieving data from API.")
}