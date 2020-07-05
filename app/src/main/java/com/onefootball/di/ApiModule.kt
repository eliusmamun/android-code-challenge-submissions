package com.onefootball.di

import android.content.Context
import com.onefootball.NewsApplication
import com.onefootball.model.NewsService
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

    @Provides
    fun provideFactsService() : NewsService {
        return NewsService()
    }

    @Provides
    fun provideContext() : Context {
        return NewsApplication.context
    }
}