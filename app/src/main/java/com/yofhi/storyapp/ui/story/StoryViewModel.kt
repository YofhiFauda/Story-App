package com.yofhi.storyapp.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yofhi.storyapp.data.repo.StoryRepo
import com.yofhi.storyapp.data.repo.UserRepo
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StoryViewModel(private val userRepo: UserRepo, private val storyRepo: StoryRepo) : ViewModel() {

    fun getToken() : LiveData<String> {
        return userRepo.getToken().asLiveData()
    }

    fun uploadStory(token: String, imageMultipart: MultipartBody.Part, desc: RequestBody) = storyRepo.uploadStory(token, imageMultipart, desc)
}