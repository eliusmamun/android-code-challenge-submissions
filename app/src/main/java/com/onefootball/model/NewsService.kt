package com.onefootball.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.onefootball.di.DaggerApiComponent
import org.json.JSONObject
import java.nio.charset.Charset
import javax.inject.Inject

 class NewsService {

    init {
        DaggerApiComponent.create().inject(this)
    }


    @Inject
    lateinit var context: Context

    fun getNewsData(): List<News> {

        val inputStream = context.assets.open("news.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        val jsonString = buffer.toString(Charset.defaultCharset())
        val newsObject = JSONObject(jsonString).get("news")

        return Gson().fromJson(newsObject.toString(),
            object : TypeToken<List<News>>() {}.type
        )
    }
}