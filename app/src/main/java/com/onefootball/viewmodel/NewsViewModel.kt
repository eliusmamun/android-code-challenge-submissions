package com.onefootball.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onefootball.di.DaggerApiComponent
import com.onefootball.model.News
import com.onefootball.model.NewsService
import com.onefootball.utils.EspressoIdlingResource
import kotlinx.coroutines.*
import javax.inject.Inject


class NewsViewModel(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    @Inject
    lateinit var newsService : NewsService


    init {
        DaggerApiComponent.create().inject(this)
    }

     private val news: MutableLiveData<News> by lazy {
        fetchNews()
        MutableLiveData<News>()
    }

     val newsLoadError = MutableLiveData<String?>()
     val loading = MutableLiveData<Boolean>()

    //private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        viewModelScope.launch {
            onError("Exception: ${throwable.localizedMessage}")
        }
    }


    fun getNewsObservable(): MutableLiveData<News> {
        return news
    }

    fun refresh(){
        fetchNews()
    }

    /**
     * Fetches news from the json file.
     * viewModelScope by default runs on main thread.
     */
     private fun fetchNews() {
        EspressoIdlingResource.increment()
        loading.value = true
         viewModelScope.launch(exceptionHandler) {
            val newsData = withContext(ioDispatcher){
                newsService.getNewsData()
            }
                news.value = newsData
                newsLoadError.value = null
                loading.value = false

            EspressoIdlingResource.decrement()
        }
    }


    /**
     * Handles the error
     */
    private fun onError(message: String) {
        newsLoadError.value = message
        loading.value = false
    }

   /* override fun onCleared() {
       super.onCleared()
        job?.cancel()
    }*/

}