package fi.oamk.news_app.model

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import fi.oamk.news_app.viewmodel.CategoryViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.Objects

interface SearchArticlesApi {
    @GET("/v2/everything?apiKey=1ee3e762b1064d258d2d5faa8f2a0dc5")
    suspend fun getArticles(@retrofit2.http.Query("q") search: String,
                            @retrofit2.http.Query("language") language: String,
                            @retrofit2.http.Query("sortBy") sortBy: String) : Article
    companion object {
        var articlesService : SearchArticlesApi? = null

        fun getInstance(): SearchArticlesApi {
            if(articlesService === null){
                articlesService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(SearchArticlesApi::class.java)
            }
            return articlesService!!
        }
    }
}