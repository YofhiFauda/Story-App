package com.yofhi.storyapp.ui.register

import androidx.lifecycle.ViewModel
import com.yofhi.storyapp.data.repo.UserRepo

class SignUpViewModel(private val repo: UserRepo) : ViewModel() {

    fun register(name: String, email: String, password: String) = repo.register(name, email, password)
}