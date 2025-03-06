package fi.oamk.news_app.viewmodel

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import fi.oamk.news_app.model.Article
import fi.oamk.news_app.model.ArticlesApi
import fi.oamk.news_app.model.SearchArticlesApi
import kotlinx.coroutines.launch
import java.util.Objects

interface ArticleSearchUiState {
    data class Success(val articles: Article): ArticleSearchUiState
    object Error: ArticleSearchUiState
    object NoRequest: ArticleSearchUiState
}


class ArticlesSearchViewModel: ViewModel()
{
    var articleUiState: ArticleSearchUiState by
    mutableStateOf<ArticleSearchUiState>(ArticleSearchUiState.NoRequest) //all data fetched from API is stored here (if succeeded)
        private set

    fun getArticlesList(searchPhrase: String,sortBy: String,language:String) {
        viewModelScope.launch {
            var articlesApi: SearchArticlesApi? = null
            try {
                articlesApi = SearchArticlesApi.getInstance()
                articleUiState = ArticleSearchUiState.Success(articlesApi.getArticles(searchPhrase,sortBy,language))
            } catch (e: Exception) {
                Log.d("ERROR",e.message.toString())
                articleUiState = ArticleSearchUiState.Error
            }
        }
    }
}