package com.onefootball.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onefootball.di.DaggerApiComponent
import com.onefootball.model.News
import com.onefootball.model.NewsService
import kotlinx.coroutines.*
import javax.inject.Inject


class NewsViewModel : ViewModel() {

    @Inject
    lateinit var newsService : NewsService


    init {
        DaggerApiComponent.create().inject(this)
    }

     val news: MutableLiveData<List<News>> by lazy {
        fetchNews()
        MutableLiveData<List<News>>()
    }

     val newsLoadError = MutableLiveData<String?>()
     val loading = MutableLiveData<Boolean>()

    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        CoroutineScope(Dispatchers.Main).launch {
            onError("Exception: ${throwable.localizedMessage}")
        }
    }


    fun getNewsObservable(): MutableLiveData<List<News>> {
        return news
    }

    fun refresh(){
        fetchNews()
    }

    /**
     * Fetches news from the json file
     */
     private fun fetchNews() {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val newsData = newsService.getNewsData()
            withContext(Dispatchers.Main) {
                news.value = newsData
                newsLoadError.value = null
                loading.value = false
            }
        }
    }


    /**
     * Handles the error
     */
    private fun onError(message: String) {
        newsLoadError.value = message
        loading.value = false
    }

    override fun onCleared() {
       super.onCleared()
        job?.cancel()
    }

}