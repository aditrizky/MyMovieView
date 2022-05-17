package com.binar.mymovieview.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.binar.mymovieview.MyApplication
import com.binar.mymovieview.databinding.FragmentHomeBinding
import com.binar.mymovieview.ui.ViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi



@DelicateCoroutinesApi
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!
    lateinit var viewModel: UserHomeViewModel


   /* private val remoteDataSource= MoviesRemoteDataSource()
    private val repository = MoviesRepository(remoteDataSource)*/
    val homeViewModel by viewModels<HomeViewModel> { HomeViewModelFactory((activity?.application as MyApplication).repository) }

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
        val factory = ViewModelFactory(view.context)
        viewModel= ViewModelProvider(requireActivity(),factory)[UserHomeViewModel::class.java]
        setUsername()
     //   viewModel.getUserData()
        getAllMovie()
        binding.profileButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToProfileFragment())
        }

        binding.favoriteButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragment2ToFavoriteFragment())
        }

    }
    private fun setUsername(){
        viewModel.getUsername().observe(viewLifecycleOwner){
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
}