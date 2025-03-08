package fi.oamk.news_app.model

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import fi.oamk.news_app.viewmodel.CategoryViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.Objects

data class Success(val todos: List<Article>)

data class Source(
    var id: String?,
    var name: String?
)

data class Content (
    var source: Source,
    var author: String?,
    var title: String?,
    var description: String?,
    var url: String,
    var urlToImage: String,
    var publishedAt: String?,
    var content: String
)

data class Article (
    var status: String,
    var totalResults: Int,
    var articles: Array<Content>,
    var completed: Boolean
)

const val BASE_URL = "https://newsapi.org"
interface ArticlesApi {

    @GET("/v2/top-headlines?country=us&apiKey=1c4a22eca59e4e24bbdd41480955facc")
    suspend fun getArticles(@retrofit2.http.Query("category") category: String) : Article //This function can be called only from getInstance() function outside of this interface,
    //which contains Retrofit structure converting json response fields from GET request to an object
    //retrofit2.http.Query allows to insert query data into request dynamically
    companion object {
        var articlesService : ArticlesApi? = null

        fun getInstance(): ArticlesApi {
            if(articlesService === null){
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

                val client = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
                articlesService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ArticlesApi::class.java)
            }
            return articlesService!!
        }
    }
}