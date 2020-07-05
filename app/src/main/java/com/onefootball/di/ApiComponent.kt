package com.onefootball.di

import com.onefootball.viewmodel.NewsViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(newsViewModel: NewsViewModel)
}