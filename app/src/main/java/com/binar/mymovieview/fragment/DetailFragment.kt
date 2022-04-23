package com.binar.mymovieview.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.binar.mymovieview.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide

class DetailFragment : Fragment() {
    val args : DetailFragmentArgs by navArgs()
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireView())
            .load("https://image.tmdb.org/t/p/original/"+args.detail.backdropPath)
            .into(binding.backdropImageView)
        Glide.with(requireView())
            .load("https://image.tmdb.org/t/p/original/"+args.detail.posterPath)
            .into(binding.imageView3)
        binding.titletextView.text=args.detail.title.toString()
        binding.ratingTextView.text=args.detail.voteAverage.toString()
        binding.textView7.text=args.detail.voteCount.toString()
        binding.releasTextView.text=args.detail.releaseDate.toString()
        binding.langageTextView.text=args.detail.originalLanguage.toString()
        binding.overviewTextView.text=args.detail.overview

    }
}