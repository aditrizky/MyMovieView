package com.binar.mymovieview.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.binar.mymovieview.data.local.favorite.FavoriteMovie
import com.binar.mymovieview.databinding.MovieItemBinding
import com.bumptech.glide.Glide

class FavoriteAdapter(private val item: List<FavoriteMovie>): RecyclerView.Adapter<FavoriteAdapter.MainViewHolder>()  {
    class MainViewHolder(val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.binding.titleTextView.text= item[position].title
        holder.binding.textView4.text= item[position].overview
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/original/"+item[position].poster)
            .into(holder.binding.posterImageView2)
    }

    override fun getItemCount(): Int {
        return item.size
    }
}