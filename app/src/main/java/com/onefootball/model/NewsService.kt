package com.onefootball.model

import android.content.Context
import com.google.gson.Gson
import com.onefootball.di.DaggerApiComponent
import java.nio.charset.Charset
import javax.inject.Inject

 class NewsService {

    init {
        DaggerApiComponent.create().inject(this)
    }


    @Inject
    lateinit var context: Context

      fun getNewsData(): News {

        val inputStream = context.assets.open("news.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val jsonString = buffer.toString(Charset.defaultCharset())
        return Gson().fromJson(jsonString, News::class.java)
    }
}