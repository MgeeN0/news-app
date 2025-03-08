package fi.oamk.news_app.model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchArticlesApi {
    @GET("/v2/everything?apiKey=1c4a22eca59e4e24bbdd41480955facc")
    suspend fun getArticles(
        @Query("q") search: String,
        @Query("language") language: String,
        @Query("sortBy") sortBy: String
    ): Article

    companion object {
        private var articlesService: SearchArticlesApi? = null
        private const val BASE_URL = "https://newsapi.org"

        fun getInstance(): SearchArticlesApi {
            if (articlesService == null) {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

                val client = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()

                articlesService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client) // Attach logging interceptor
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(SearchArticlesApi::class.java)
            }
            return articlesService!!
        }
    }
}
