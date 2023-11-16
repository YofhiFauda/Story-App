package com.yofhi.storyapp.di

import com.yofhi.storyapp.data.repo.StoryRepo
import com.yofhi.storyapp.data.retrofit.ApiConfig

object StoryInject {
    fun provideRepository(): StoryRepo {
        val apiService = ApiConfig.getApiService()
        return StoryRepo.getInstance(apiService)
    }
}