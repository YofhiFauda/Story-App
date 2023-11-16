package com.yofhi.storyapp.ui.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yofhi.storyapp.R
import com.yofhi.storyapp.adapter.StoryAdapter
import com.yofhi.storyapp.data.response.ListStoryItem
import com.yofhi.storyapp.data.result.Result
import com.yofhi.storyapp.databinding.ActivityMainBinding
import com.yofhi.storyapp.ui.detail.DetailActivity
import com.yofhi.storyapp.ui.factory.FactoryStoryViewModel
import com.yofhi.storyapp.ui.onboard.OnBoardingActivity
import com.yofhi.storyapp.ui.story.StoryActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainVM: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvStories.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvStories.layoutManager = LinearLayoutManager(this)
        }

        title = "Dashboard"
        setupViewModel()
    }

    private fun setupViewModel() {
        val factoryStoryVM: FactoryStoryViewModel = FactoryStoryViewModel.getInstance(this)
        mainVM = ViewModelProvider(this, factoryStoryVM)[MainViewModel::class.java]

        mainVM.isLogin().observe(this){ isLoggedIn ->
            if (!isLoggedIn){
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            } else {
                mainVM.getToken().observe(this){ token ->
                    if (token.isNotEmpty()){
                        mainVM.getStories(token).observe(this){ result ->
                            if (result != null){
                                when(result) {
                                    is Result.Loading -> {
                                        binding.progressBar.visibility = View.VISIBLE
                                    }
                                    is Result.Success -> {
                                        binding.progressBar.visibility = View.GONE
                                        val stories = result.data.listStory
                                        val listStoryAdapter = StoryAdapter(stories as ArrayList<ListStoryItem>)
                                        binding.rvStories.adapter = listStoryAdapter

                                        listStoryAdapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
                                            override fun onItemClicked(data: ListStoryItem) {
                                                showSelectedStory(data)
                                            }
                                        })
                                    }
                                    is Result.Error -> {
                                        binding.progressBar.visibility = View.GONE
                                        Toast.makeText(
                                            this,
                                            "Failure : " + result.error,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showSelectedStory(story: ListStoryItem) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_STORY, story)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.item_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                mainVM.logout()
                true
            }
            R.id.add_story -> {
                startActivity(Intent(this, StoryActivity::class.java))
                true
            }
            R.id.setting -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            else -> true
        }
    }
}