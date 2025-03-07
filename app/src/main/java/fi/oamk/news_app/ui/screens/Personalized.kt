package fi.oamk.news_app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
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
fun Personalized(articlesViewModel: ArticlesSearchViewModel = viewModel(),searchOptionsViewModel: SearchOptionsViewModel = viewModel()) {
    //articlesViewModel.getArticlesList(searchOptionsViewModel.searchPhrase,searchOptionsViewModel.selectedSorting,searchOptionsViewModel.language)
    val sortItems = listOf("Relevancy", "Popularity", "Upload date")
    val languageItems = listOf(
        "German",
        "English",
        "Spanish",
        "French",
        "Italian",
        "Dutch",
        "Norwegian",
        "Portuguese",
        "Russian",
        "Swedish",
        "Chinese"
    )
    SearchTopNewsBar()
        SearchArticlesScreen(articlesViewModel.articleUiState)
    Column(
        modifier = Modifier.padding(top = 120.dp, start = 8.dp, end = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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
                onClick = {
                    articlesViewModel.getArticlesList(searchOptionsViewModel.searchPhrase,searchOptionsViewModel.language,searchOptionsViewModel.selectedSorting)
                          searchOptionsViewModel.searchBar = "Searching phrase: " + searchOptionsViewModel.searchPhrase
                          searchOptionsViewModel.canSearch = true
                          },
                content = { Text("Search") }
            )
        }
        Row(
            modifier = Modifier,
        ) {
            Column(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
            ) {
                OutlinedTextField(
                    readOnly = true,
                    value = searchOptionsViewModel.getText(searchOptionsViewModel.selectedSorting),
                    onValueChange = { searchOptionsViewModel.selectedSorting = it },
                    modifier = Modifier.width(160.dp)
                        .height(60.dp)
                        .onGloballyPositioned { coordinates ->
                            searchOptionsViewModel.sortTextFieldSize = coordinates.size.toSize()
                },
                    label = { Text("Sort by") },
                    trailingIcon = {
                        Icon(searchOptionsViewModel.getIcon(searchOptionsViewModel.expanded),
                            "Sort",
                            Modifier.clickable { searchOptionsViewModel.setExpanded() })
                    }
                )
                /*
                Surface(
                    modifier = Modifier
                        .clickable { searchOptionsViewModel.setExpanded() },
                    shape = CircleShape,
                    color = Color.Magenta,
                    shadowElevation = 8.dp
                ) {
                    Text("Sort by", modifier = Modifier.padding(10.dp))
                }
                 */
                DropdownMenu(
                    expanded = searchOptionsViewModel.expanded,
                    onDismissRequest = { searchOptionsViewModel.setFalse() },
                    modifier = Modifier.width(with(LocalDensity.current){searchOptionsViewModel.sortTextFieldSize.width.toDp()})

                ) {
                    /*
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

                     */

                    sortItems.forEach { item ->
                        val sort: String = when (item) {
                            "Relevancy" -> "relevancy"
                            "Popularity" -> "popularity"
                            "Upload date" -> "publishedAt"
                            else -> "relevancy"
                        }
                        DropdownMenuItem(
                            text = { Text(item) },
                            onClick = {
                                searchOptionsViewModel.selectSorting(sort)
                                searchOptionsViewModel.setFalse()
                                if(searchOptionsViewModel.canSearch) {
                                    articlesViewModel.getArticlesList(searchOptionsViewModel.searchPhrase,searchOptionsViewModel.language,searchOptionsViewModel.selectedSorting)
                                }
                            }
                        )
                    }
                }
            }
                Column(
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
                ) {
                    /*
                Surface(
                    modifier = Modifier.padding(start=8.dp,top = 8.dp)
                        .clickable { searchOptionsViewModel.setLExpanded() },
                    shape = CircleShape,
                    color = Color.Magenta,
                    shadowElevation = 8.dp
                ) {
                    Text("Language", modifier = Modifier.padding(10.dp))
                }
                 */
                    OutlinedTextField(
                        modifier = Modifier.width(160.dp)
                            .height(60.dp)
                            .onGloballyPositioned { coordinates ->
                                searchOptionsViewModel.languageTextFieldSize = coordinates.size.toSize() },
                        readOnly = true,
                        value = searchOptionsViewModel.getText(searchOptionsViewModel.language),
                        onValueChange = { searchOptionsViewModel.language = it },
                        label = { Text("Language") },
                        trailingIcon = {
                            Icon(searchOptionsViewModel.getIcon(searchOptionsViewModel.languageExpanded),
                                "language",
                                Modifier.clickable { searchOptionsViewModel.setLExpanded() })
                        }
                    )
                    DropdownMenu(
                        expanded = searchOptionsViewModel.languageExpanded,
                        onDismissRequest = { searchOptionsViewModel.setLFalse() },
                        modifier = Modifier.width(with(LocalDensity.current){searchOptionsViewModel.languageTextFieldSize.width.toDp()})
                    ) {
                        languageItems.forEach { item ->
                            val lang: String = when (item) {
                                "German" -> "de"
                                "English" -> "en"
                                "Spanish" -> "es"
                                "French" -> "fr"
                                "Italian" -> "it"
                                "Dutch" -> "nl"
                                "Norwegian" -> "no"
                                "Portuguese" -> "pt"
                                "Russian" -> "ru"
                                "Swedish" -> "sv"
                                "Chinese" -> "zh"
                                else -> "en"
                            }
                            DropdownMenuItem(
                                text = { Text(item) },
                                onClick = {
                                    searchOptionsViewModel.selectLanguage(lang)
                                    searchOptionsViewModel.setLFalse()
                                    if(searchOptionsViewModel.canSearch) {
                                        articlesViewModel.getArticlesList(searchOptionsViewModel.searchPhrase,searchOptionsViewModel.language,searchOptionsViewModel.selectedSorting)
                                    }
                                }
                            )
                        }
                        /*
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

                         */
                    }
                }
            }
        }
    }

@Composable
fun SearchArticlesScreen(uiState: ArticleSearchUiState) {
    val modifier = Modifier.padding(top=265.dp)
    when (uiState) {
        is ArticleSearchUiState.NoRequest -> EmptyScreen()
        is ArticleSearchUiState.Success -> NewsCards(modifier,uiState.articles)
        is ArticleSearchUiState.Error -> SearchErrorScreen()
    }
}

@Composable
fun EmptyScreen() {
    Text(
        modifier = Modifier.padding(vertical = 265.dp, horizontal = 10.dp),
        text = "articles will appear here..."
    )
}

@Composable
fun SearchErrorScreen() {
    Text(
        modifier = Modifier.padding(vertical = 265.dp, horizontal = 10.dp),
        text = "Error retrieving data from API.")
}