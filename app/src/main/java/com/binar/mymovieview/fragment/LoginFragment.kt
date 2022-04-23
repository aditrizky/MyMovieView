package com.binar.mymovieview.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.binar.mymovieview.databinding.FragmentLoginBinding
import com.binar.mymovieview.mvvm.UserAuthViewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!
    private val viewModel : UserAuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserData()
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditTextText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(requireContext(),"Empty Fields",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.getInputan(email,password)
                viewModel.loginAuth()
            }
        }
        binding.toCreateTextView.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        navigateUi()

    }
    private fun navigateUi(){
        viewModel.getvalidation().observe(viewLifecycleOwner){
            if(it!= 0){
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment2())
                Log.d("login", "testing berhasil $it")
            }else{
                Log.d("login", "testing $it")
            }
        }
    }

    }

