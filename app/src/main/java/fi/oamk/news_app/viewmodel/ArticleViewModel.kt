package fi.oamk.news_app.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fi.oamk.news_app.model.Article
import fi.oamk.news_app.model.ArticlesApi
import kotlinx.coroutines.launch

sealed interface NewsUiState {
    data class Success(val todos: List<Article>): NewsUiState
    object Error: NewsUiState
    object Loading: NewsUiState
}

class ToDoViewModel: ViewModel()
{
    var articleUiState: NewsUiState by
    mutableStateOf<NewsUiState>(NewsUiState.Loading) //all data fetched from API is stored here (if succeeded)
        private set

    init {
        getTodosList()
    }
    private fun getTodosList() {
        viewModelScope.launch {
            var articlesApi: ArticlesApi? = null
            try {
                articlesApi = ArticlesApi.getInstance()
                articleUiState = NewsUiState.Success(articlesApi.getArticles())
            } catch (e: Exception) {
                articleUiState = NewsUiState.Error
            }
        }
    }
}