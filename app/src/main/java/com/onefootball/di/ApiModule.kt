package com.onefootball.di

import android.content.Context
import com.onefootball.NewsApplication
import com.onefootball.model.NewsService
import com.onefootball.utils.APPContext
import dagger.Module
import dagger.Provides

@Module
  class ApiModule {

    @Provides
     fun provideNewsService() : NewsService {
        return NewsService()
    }

    @Provides
     fun provideContext() : Context {
        return APPContext.getContext()
    }
}