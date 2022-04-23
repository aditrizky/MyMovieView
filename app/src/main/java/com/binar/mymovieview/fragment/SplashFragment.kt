package com.binar.mymovieview.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.binar.mymovieview.R
import com.binar.mymovieview.databinding.FragmentLoginBinding
import com.binar.mymovieview.databinding.FragmentSplashBinding
import com.binar.mymovieview.mvvm.UserAuthViewModel

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding get() = _binding!!
    private val viewModel : UserAuthViewModel by activityViewModels()
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
        viewModel.loginCek()
        Handler().postDelayed({
           navigateUi()
        },2000)
    }
    fun navigateUi(){
        viewModel.getvalidation().observe(viewLifecycleOwner){
            if(it== 1){
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment2())
                Log.d("login", "testing berhasil $it")
            }else if (it==0){
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                Log.d("login", "testing $it")
            }
        }
    }

}