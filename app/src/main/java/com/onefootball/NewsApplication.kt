package com.onefootball

import android.app.Application
import com.onefootball.utils.APPContext

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        APPContext.setContext(applicationContext)
    }
}