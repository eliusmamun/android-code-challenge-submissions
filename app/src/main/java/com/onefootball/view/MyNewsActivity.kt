package com.onefootball.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.onefootball.R
import com.onefootball.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MyNewsActivity : AppCompatActivity() {

    var myAdapter = NewsAdapter()

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = myAdapter
        }

        observeViewModel()
    }

    /**
     * Register the observers
     */
    private fun observeViewModel() {
        viewModel.getNewsObservable().observe(this, Observer { news ->
            news?.let {
                newsRecyclerView.visibility = View.VISIBLE
                myAdapter.setNewsItems(news)
            }
        })

        viewModel.newsLoadError.observe(this, Observer { isError ->
            list_error.visibility = if (isError == null) View.GONE else View.VISIBLE
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    list_error.visibility = View.GONE
                    newsRecyclerView.visibility = View.GONE
                }
            }
        })
    }

}
