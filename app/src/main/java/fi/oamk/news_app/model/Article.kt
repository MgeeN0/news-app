package fi.oamk.news_app.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.Objects

data class Success(val todos: List<Article>)

data class Source(
    var id: String?,
    var name: String
)

data class Content (
    var source: Array<Source>,
    var author: String,
    var title: String,
    var description: String,
    var url: String,
    var urlToImage: String,
    var publishedAt: String,
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
    @GET("/v2/everything?apiKey=1ee3e762b1064d258d2d5faa8f2a0dc5")
    suspend fun getArticles() : List<Article> //This function can be called only from getInstance() function outside of this interface,
    //which contains Retrofit structure converting json response fields from GET request to an object
    companion object {
        var articlesService : ArticlesApi? = null

        fun getInstance(): ArticlesApi {
            if(articlesService === null){
                articlesService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ArticlesApi::class.java)
            }
            return articlesService!!
        }
    }
}