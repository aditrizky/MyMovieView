package com.binar.mymovieview.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.mymovieview.R
import com.binar.mymovieview.databinding.FragmentDetailBinding
import com.binar.mymovieview.databinding.FragmentFavoriteBinding
import com.binar.mymovieview.ui.FavoriteMovieViewModelFactory
import com.binar.mymovieview.ui.detail.DetailViewModel
import com.binar.mymovieview.ui.home.MainAdapter


class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding get() = _binding!!
    lateinit var viewModel : FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = FavoriteMovieViewModelFactory(view.context)
        viewModel= ViewModelProvider(requireActivity(),factory)[FavoriteViewModel::class.java]
        viewModel.getAllFavorite()
        getAllFavorite()
    }

    private fun getAllFavorite() {
       viewModel.getMovie().observe(viewLifecycleOwner) {
            val adapter = FavoriteAdapter(it)
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.recycleview.layoutManager = layoutManager
            binding.recycleview.adapter = adapter
        }

    }
}