package com.onefootball.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onefootball.model.News
import com.onefootball.model.NewsService
import kotlinx.coroutines.*

class NewsViewModel : ViewModel() {

    var newsService = NewsService()

    private val news: MutableLiveData<List<News>> by lazy {
        fetchNews()
        MutableLiveData<List<News>>()
    }

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        CoroutineScope(Dispatchers.Main).launch {
            onError("Exception: ${throwable.localizedMessage}")
        }
    }


    fun getNewsObservable(): MutableLiveData<List<News>> {
        return news
    }

    /**
     * Fetches news from the json file
     */
    private fun fetchNews() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val newsData = newsService.getNewsData()
            withContext(Dispatchers.Main) {
                news.value = newsData
            }
        }
    }


    /**
     * Handles the Error
     */
    private fun onError(message: String) {

    }

    override fun onCleared() {
       super.onCleared()
        job?.cancel()
    }

}