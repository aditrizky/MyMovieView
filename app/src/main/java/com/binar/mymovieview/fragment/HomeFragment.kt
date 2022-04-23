package com.binar.mymovieview.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.mymovieview.adapter.MainAdapter
import com.binar.mymovieview.databinding.FragmentHomeBinding
import com.binar.mymovieview.mvvm.MovieViewModel
import com.binar.mymovieview.mvvm.UserAuthViewModel


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    private val viewModel : UserAuthViewModel by activityViewModels()
    private val movieViewModel : MovieViewModel by activityViewModels()
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
        viewModel.getUserData()
        getAllMovie()
        binding.profileButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToProfileFragment())
        }

    }
    private fun setUsername(){
        viewModel.username.observe(viewLifecycleOwner){
            binding.usernameTextView.text=it.toString()
        }
    }
    private fun getAllMovie() {
        movieViewModel.getMovie().observe(viewLifecycleOwner) {
            val adapter = MainAdapter(it)
            val layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            binding.recycleview.layoutManager = layoutManager
            binding.recycleview.adapter = adapter
        }
        movieViewModel.getCode().observe(viewLifecycleOwner) { codeResponse ->
            if (codeResponse == 200) {
                binding.progressBar.visibility = View.GONE
            }
        }
    }
}