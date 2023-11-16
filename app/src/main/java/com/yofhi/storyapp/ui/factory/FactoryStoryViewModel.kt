package com.yofhi.storyapp.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yofhi.storyapp.data.repo.StoryRepo
import com.yofhi.storyapp.data.repo.UserRepo
import com.yofhi.storyapp.di.StoryInject
import com.yofhi.storyapp.di.UserInject
import com.yofhi.storyapp.ui.main.MainViewModel
import com.yofhi.storyapp.ui.story.StoryViewModel

class FactoryStoryViewModel private constructor(private val userRepo: UserRepo, private val storyRepo: StoryRepo) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userRepo, storyRepo) as T
            }
            modelClass.isAssignableFrom(StoryViewModel::class.java) -> {
                StoryViewModel(userRepo, storyRepo) as T
            }
            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }
    }

    companion object {
        @Volatile
        private var instance: FactoryStoryViewModel? = null
        fun getInstance(context: Context): FactoryStoryViewModel =
            instance ?: synchronized(this) {
                instance ?: FactoryStoryViewModel(UserInject.provideRepository(context), StoryInject.provideRepository())
            }.also { instance = it }
    }
}