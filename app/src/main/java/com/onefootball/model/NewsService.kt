package com.onefootball.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.onefootball.NewsApplication
import org.json.JSONObject
import java.nio.charset.Charset

class NewsService {

    fun getNewsData(): List<News> {

        val inputStream = NewsApplication.context.assets.open("news.json")
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