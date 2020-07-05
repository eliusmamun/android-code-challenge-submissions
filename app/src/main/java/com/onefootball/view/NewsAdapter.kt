package com.onefootball.view

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.onefootball.R
import com.onefootball.databinding.NewsItemBinding
import com.onefootball.model.News

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val newsItems = ArrayList<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: NewsItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.news_item, parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount() = newsItems.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsItems[position]
        holder.bind(news)
        holder.itemView.setOnClickListener {
            it.context.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(news.newsLink))
            )

        }
    }

    fun setNewsItems(newListOfNewsItems: List<News>) {
        newsItems.clear()
        newsItems.addAll(newListOfNewsItems)
        notifyDataSetChanged()
    }

    class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.apply {
                newsView = news.imageURL?:""
                newsTitle = news.title
                resourceIcon = news.resourceURL?:""
                resourceName = news.resourceName
            }
        }
    }

}