package com.onefootball

import android.app.Application
import android.content.Context

class NewsApplication : Application() {

    init { INSTANCE = this }

    companion object {
        lateinit var INSTANCE: NewsApplication
        val context: Context
            get() { return INSTANCE.applicationContext }
    }
}