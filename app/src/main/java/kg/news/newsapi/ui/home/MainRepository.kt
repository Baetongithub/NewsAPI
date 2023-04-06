package kg.news.newsapi.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kg.news.newsapi.data.model.NewsModel
import kg.news.newsapi.data.remote.network.NewsAPI
import kg.news.newsapi.data.remote.network.Resource
import kotlinx.coroutines.Dispatchers

class MainRepository(val newsApi: NewsAPI) {
    fun loadRequestedNews(query: String?): LiveData<Resource<NewsModel?>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            val response = newsApi.getEverything(query)
            emit(
                if (response.isSuccessful) {
                    Resource.success(response.body())
                } else {
                    Resource.error(response.message(), response.body(), response.code())
                }
            )
        }
    }

    fun loadTopRatedNews(country: String, category: String?): LiveData<Resource<NewsModel?>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            if (category == null) {
                val response = newsApi.topHeadlines(country, null)
                emit(
                    if (response.isSuccessful) {
                        Resource.success(response.body())
                    } else {
                        Resource.error(response.message(), response.body(), response.code())
                    }
                )
            } else {
                val response = newsApi.topHeadlines(country, category)
                emit(
                    if (response.isSuccessful) {
                        Resource.success(response.body())
                    } else {
                        Resource.error(response.message(), response.body(), response.code())
                    }
                )
            }
        }
    }
}