package com.binar.mymovieview.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.mymovieview.databinding.FragmentFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding get() = _binding!!
    private val viewModel : FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllFavorite()
        fetchAllFavorite()

    }

    private fun fetchAllFavorite() {
       viewModel.getMovie().observe(viewLifecycleOwner) {
            val adapter = FavoriteAdapter(it)
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.recycleview.layoutManager = layoutManager
            binding.recycleview.adapter = adapter
        }

    }
}