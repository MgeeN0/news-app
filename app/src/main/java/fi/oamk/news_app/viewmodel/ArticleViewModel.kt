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
import kotlinx.coroutines.launch
import java.util.Objects

interface NewsUiState {
    data class Success(val articles: Article): NewsUiState
    object Error: NewsUiState
    object Loading: NewsUiState
}


class ArticlesViewModel: ViewModel()

{
    var articleUiState: NewsUiState by
    mutableStateOf<NewsUiState>(NewsUiState.Loading) //all data fetched from API is stored here (if succeeded)
        private set

    fun getArticlesList(selectedCategory: String) {
        viewModelScope.launch {
            var articlesApi: ArticlesApi? = null
            try {
                articlesApi = ArticlesApi.getInstance()
                articleUiState = NewsUiState.Success(articlesApi.getArticles(selectedCategory))
            } catch (e: Exception) {
                Log.d("ERROR",e.message.toString())
                articleUiState = NewsUiState.Error
            }
        }
    }
}