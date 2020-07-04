package com.onefootball.model

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class NewsService {

    var jsonString: String? = null


    fun getNewsData(context: Context): List<News> {

        var inputStream = context.assets.open("news.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        jsonString = buffer.toString(Charset.defaultCharset())
        val newsService = NewsService()
        val items = newsService.parseJsonString(jsonString!!)
        return items

    }

    private fun parseJsonString(jsonString: String): List<News> {
        val mainObject = JSONObject(jsonString)
        val newsItems = mutableListOf<News>()
        val newsArray = mainObject.getJSONArray("news")
        newsArray.forEach { newsObject ->
            val title = newsObject.getString("title")
            val imageURL = newsObject.getString("image_url")
            val resourceName = newsObject.getString("resource_name")
            val resourceURL = newsObject.getString("resource_url")
            val newsLink = newsObject.getString("news_link")

            newsItems.add(
                News(
                    title = title,
                    imageURL = imageURL,
                    resourceName = resourceName,
                    resourceURL = resourceURL,
                    newsLink = newsLink
                )
            )
        }
        return newsItems
    }

    fun JSONArray.forEach(jsonObject: (JSONObject) -> Unit) {
        for (index in 0 until this.length()) jsonObject(this[index] as JSONObject)
    }
}