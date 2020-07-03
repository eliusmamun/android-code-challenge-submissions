package com.onefootball

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.onefootball.model.News
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class MyNewsActivity : AppCompatActivity() {

    var jsonString: String? = null
    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.newsRecyclerView)
        myAdapter = NewsAdapter()
        with(recyclerView) {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(this@MyNewsActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        var inputStream = assets.open("news.json")
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        jsonString = buffer.toString(Charset.defaultCharset())

        val items = parseJsonString(jsonString!!)
        myAdapter.setNewsItems(items)
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

            newsItems.add(News(title = title, imageURL = imageURL, resourceName = resourceName, resourceURL = resourceURL, newsLink = newsLink))
        }
        return newsItems
    }
}

fun JSONArray.forEach(jsonObject: (JSONObject) -> Unit) {
    for (index in 0 until this.length()) jsonObject(this[index] as JSONObject)
}
