package com.binar.mymovieview.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.mymovieview.databinding.FragmentHomeBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@DelicateCoroutinesApi
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUsername()
        getAllMovie()
        binding.profileButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToProfileFragment())
        }

        binding.favoriteButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToFavoriteFragment())
        }

    }
    private fun setUsername(){
        homeViewModel.getUsername().observe(viewLifecycleOwner){
            binding.usernameTextView.text=it.toString()
        }
    }
    private fun getAllMovie() {
        homeViewModel.getMovie().observe(viewLifecycleOwner) {
            val adapter = MainAdapter(it)
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.recycleview.layoutManager = layoutManager
            binding.recycleview.adapter = adapter
        }
        homeViewModel.getCode().observe(viewLifecycleOwner) { codeResponse ->
            if (codeResponse == 200) {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}