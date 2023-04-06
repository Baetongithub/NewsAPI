package kg.news.newsapi.data.remote.network

import kg.news.newsapi.BuildConfig
import kg.news.newsapi.data.model.NewsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    @GET("everything")
    suspend fun getEverything(
        @Query("q")
        q: String?,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY,
    ): Response<NewsModel>

    @GET("top-headlines")
    suspend fun topHeadlines(
        @Query("country")
        country: String?,
        @Query("category")
        category: String?,
        @Query("apiKey")
        apiKey: String = BuildConfig.API_KEY,
    ): Response<NewsModel>

//    @GET("top-headlines/sources")
//    suspend fun topRatedCategories(
//        @Query("category")
//        category: String?,
//        @Query("apiKey")
//        apiKey: String = BuildConfig.API_KEY,
//    ): Response<TopRatedCategoriesModel>
}