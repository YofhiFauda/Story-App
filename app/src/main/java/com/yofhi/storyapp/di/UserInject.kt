package com.yofhi.storyapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.yofhi.storyapp.data.repo.UserRepo
import com.yofhi.storyapp.data.retrofit.ApiConfig

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object UserInject {
    fun provideRepository(context: Context): UserRepo {
        val apiService = ApiConfig.getApiService()
        return UserRepo.getInstance(context.dataStore, apiService)
    }
}