package kg.news.newsapi.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kg.news.newsapi.data.model.NewsModel
import kg.news.newsapi.data.remote.network.Resource

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    fun loadRequestedNews(query: String?): LiveData<Resource<NewsModel?>> {
        return repository.loadRequestedNews(query)
    }

    fun loadTopRatedNews(country: String, category: String?): LiveData<Resource<NewsModel?>> {
        return repository.loadTopRatedNews(country, category)
    }
}