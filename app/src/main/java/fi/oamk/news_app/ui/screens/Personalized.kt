package fi.oamk.news_app.ui.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import fi.oamk.news_app.R
import fi.oamk.news_app.model.Article
import fi.oamk.news_app.ui.appbars.SearchTopNewsBar
import fi.oamk.news_app.ui.appbars.TopNewsBar
import fi.oamk.news_app.viewmodel.ArticleSearchUiState
import fi.oamk.news_app.viewmodel.ArticlesSearchViewModel
import fi.oamk.news_app.viewmodel.ArticlesViewModel
import fi.oamk.news_app.viewmodel.NewsUiState
import fi.oamk.news_app.viewmodel.SearchOptionsViewModel

@Composable
fun Personalized(navController: NavController, modifier:Modifier, articlesViewModel: ArticlesSearchViewModel = viewModel(), searchOptionsViewModel: SearchOptionsViewModel = viewModel()) {
    Log.d("SCREEN HEIGHT",LocalConfiguration.current.screenHeightDp.dp.toString())
    val sortItems = listOf(stringResource(R.string.relevancy),
        stringResource(R.string.popularity), stringResource(R.string.upload_date)
    )
    val languageItems = listOf(
        stringResource(R.string.german),
        stringResource(R.string.english),
        stringResource(R.string.spanish),
        stringResource(R.string.french),
        stringResource(R.string.italian),
        stringResource(R.string.dutch),
        stringResource(R.string.norwegian),
        stringResource(R.string.portuguese),
        stringResource(R.string.russian),
        stringResource(R.string.swedish),
        stringResource(R.string.chinese)
    )
    SearchTopNewsBar(navController)
        SearchArticlesScreen(articlesViewModel.articleUiState)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(0.65f),
                value = searchOptionsViewModel.searchPhrase,
                onValueChange = { searchOptionsViewModel.searchPhrase = it },
                label = { Text(text = stringResource(R.string.enter_search_phrase)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Button(
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContentColor = MaterialTheme.colorScheme.tertiary,
                    disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer
                ),
                modifier = Modifier.padding(top = 12.dp, start = 12.dp)
                    .fillMaxWidth(),
                onClick = {
                    if(searchOptionsViewModel.searchPhrase != "") {
                        articlesViewModel.getArticlesList(
                            searchOptionsViewModel.searchPhrase,
                            searchOptionsViewModel.language,
                            searchOptionsViewModel.selectedSorting
                        )
                        searchOptionsViewModel.searchBar =
                            "Searching phrase: " + searchOptionsViewModel.searchPhrase
                        searchOptionsViewModel.canSearch = true
                    }
                          },
                content = { Text(stringResource(R.string.search)) }
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
                    modifier = Modifier
                        .width(160.dp)
                        .height(60.dp)
                        .onGloballyPositioned { coordinates ->
                            searchOptionsViewModel.sortTextFieldSize = coordinates.size.toSize()
                        },
                    label = { Text(stringResource(R.string.sort_by)) },
                    trailingIcon = {
                        Icon(searchOptionsViewModel.getIcon(searchOptionsViewModel.expanded),
                            stringResource(R.string.sort),
                            Modifier.clickable { searchOptionsViewModel.setExpanded() })
                    }
                )
                DropdownMenu(
                    expanded = searchOptionsViewModel.expanded,
                    onDismissRequest = { searchOptionsViewModel.setFalse() },
                    modifier = Modifier.width(with(LocalDensity.current){searchOptionsViewModel.sortTextFieldSize.width.toDp()})

                ) {
                    sortItems.forEach { item ->
                        val sort: String = when (item) {
                            stringResource(R.string.relevancy) -> stringResource(R.string.sRelevancy)
                            stringResource(R.string.popularity) -> stringResource(R.string.sPopularity)
                            stringResource(R.string.upload_date)  -> stringResource(R.string.sPublishedAt)
                            else -> stringResource(R.string.sRelevancy)
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
                    OutlinedTextField(
                        modifier = Modifier
                            .width(160.dp)
                            .height(60.dp)
                            .onGloballyPositioned { coordinates ->
                                searchOptionsViewModel.languageTextFieldSize =
                                    coordinates.size.toSize()
                            },
                        readOnly = true,
                        value = searchOptionsViewModel.getText(searchOptionsViewModel.language),
                        onValueChange = { searchOptionsViewModel.language = it },
                        label = { Text(stringResource(R.string.labelLanguage)) },
                        trailingIcon = {
                            Icon(searchOptionsViewModel.getIcon(searchOptionsViewModel.languageExpanded),
                                stringResource(R.string.language),
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
                                stringResource(R.string.german) -> stringResource(R.string.de)
                                stringResource(R.string.english) -> stringResource(R.string.en)
                                stringResource(R.string.spanish) -> stringResource(R.string.es)
                                stringResource(R.string.french) -> stringResource(R.string.fr)
                                stringResource(R.string.italian) -> stringResource(R.string.it)
                                stringResource(R.string.dutch) -> stringResource(R.string.nl)
                                stringResource(R.string.norwegian) -> stringResource(R.string.no)
                                stringResource(R.string.portuguese) -> stringResource(R.string.pt)
                                stringResource(R.string.russian) -> stringResource(R.string.ru)
                                stringResource(R.string.swedish) -> stringResource(R.string.sv)
                                stringResource(R.string.chinese) -> stringResource(R.string.zh)
                                else -> stringResource(R.string.en)
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
        is ArticleSearchUiState.Error -> ErrorScreen(Modifier.padding(start = 80.dp, top = 265.dp))
    }
}

@Composable
fun EmptyScreen() {
    Text(
        modifier = Modifier.padding(vertical = 260.dp, horizontal = 10.dp),
        text = stringResource(R.string.articles)
    )
}