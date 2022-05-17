package com.binar.mymovieview.ui.splashscreen

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.mymovieview.databinding.FragmentSplashBinding
import com.binar.mymovieview.ui.ViewModelFactory


class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding get() = _binding!!
    lateinit var viewModel : SplashViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory(view.context)
        viewModel= ViewModelProvider(requireActivity(),factory)[SplashViewModel::class.java]

//        viewModel.loginCheck()
        Handler().postDelayed({
           navigateUi()
        },2000)
    }
    fun navigateUi(){
        viewModel.loginCheck().observe(viewLifecycleOwner){
            if(it != "default"){
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment2())
                Log.d("login", "testing berhasil $it")
            }else{
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                Log.d("login", "testing $it")
            }
        }
    }

}