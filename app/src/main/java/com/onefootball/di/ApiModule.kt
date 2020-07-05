package com.onefootball.di

import com.onefootball.model.NewsService
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

    @Provides
    fun provideFactsService() : NewsService {
        return NewsService()
    }
}