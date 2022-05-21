package com.binar.mymovieview.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.binar.mymovieview.databinding.MovieItemBinding
import com.binar.mymovieview.data.remote.model.populer.model.Result
import com.bumptech.glide.Glide

class MainAdapter (private val item : List<Result>) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    class MainViewHolder(val binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        holder.binding.titleTextView.text= item[position].originalTitle
        holder.binding.textView4.text= item[position].overview
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/original/"+item[position].posterPath)
            .into(holder.binding.posterImageView2)

        holder.itemView.setOnClickListener {
        val objectDetail = Result(
            title = item[position].originalTitle,
            voteAverage = item[position].voteAverage,
            voteCount = item[position].voteCount,
            releaseDate = item[position].releaseDate,
            overview = item[position].overview,
            originalLanguage = item[position].originalLanguage,
            backdropPath = item[position].backdropPath,
            posterPath = item[position].posterPath
        )

            it.findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToDetailFragment(objectDetail))
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }

}