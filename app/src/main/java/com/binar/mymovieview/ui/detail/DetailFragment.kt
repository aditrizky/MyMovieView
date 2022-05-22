package com.binar.mymovieview.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.navigation.fragment.navArgs
import com.binar.mymovieview.data.local.favorite.FavoriteMovie
import com.binar.mymovieview.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {
    val args : DetailFragmentArgs by navArgs()
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!
    private val viewModel : DetailViewModel by viewModel()
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
            .load("https://image.tmdb.org/t/p/original/"+args.detail?.backdropPath)
            .into(binding.backdropImageView)
        Glide.with(requireView())
            .load("https://image.tmdb.org/t/p/original/"+args.detail?.posterPath)
            .into(binding.imageView3)
        binding.titletextView.text=args.detail?.title.toString()
        binding.ratingTextView.text=args.detail?.voteAverage.toString()
        binding.textView7.text=args.detail?.voteCount.toString()
        binding.releasTextView.text=args.detail?.releaseDate.toString()
        binding.langageTextView.text=args.detail?.originalLanguage.toString()
        binding.overviewTextView.text=args.detail?.overview

        binding.favoriteImageButton.setOnClickListener {
            addFavorite()
        }

    }

    private fun addFavorite(){
        val objectfavorite = FavoriteMovie(
            title = args.detail?.title,
            voteAverage = args.detail?.voteAverage,
            voteCount = args.detail?.voteCount,
            releaseDate = args.detail?.releaseDate,
            overview = args.detail?.overview,
            language = args.detail?.originalLanguage,
            backdrop = args.detail?.backdropPath,
            poster = args.detail?.posterPath
        )
        viewModel.addFavorite(objectfavorite)

        viewModel.result().observe(viewLifecycleOwner){
            if (it ==true){
                Toast.makeText(requireActivity(),"Added To Favorite",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireActivity(),"Failed Added To Favorite",Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
