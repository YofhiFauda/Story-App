package com.yofhi.storyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yofhi.storyapp.data.response.ListStoryItem
import com.yofhi.storyapp.databinding.ItemsStoryBinding

class StoryAdapter(private val listStory: ArrayList<ListStoryItem>) : RecyclerView.Adapter<StoryAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ListViewHolder(var binding: ItemsStoryBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemsStoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val story = listStory[position]
        holder.binding.apply {
            tvItemName.text = story.name
            tvDescription.text = story.description
        }
        Glide.with(holder.itemView.context)
            .load(story.photoUrl)
            .into(holder.binding.ivItemPhoto)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listStory[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listStory.size

    interface OnItemClickCallback {
        fun onItemClicked(data: ListStoryItem)
    }
}