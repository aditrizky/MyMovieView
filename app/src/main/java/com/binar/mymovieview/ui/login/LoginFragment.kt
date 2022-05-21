package com.binar.mymovieview.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.navigation.fragment.findNavController
import com.binar.mymovieview.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModel()

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


        binding.loginButton.setOnClickListener {
            val email = binding.emailEditTextText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(requireContext(),"Empty Fields",Toast.LENGTH_SHORT).show()
            }else{
                loginViewModel.authLogin(email,password)
            }
        }
        binding.toCreateTextView.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
        navigateUi()

    }
    private fun navigateUi(){
        loginViewModel.result().observe(viewLifecycleOwner){
            if(it==true){
                Toast.makeText(requireActivity(),"Login Success",Toast.LENGTH_SHORT).show()
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment2())
                Log.d("login", "testing berhasil $it")
                loginViewModel.reset()
            }else{
                Toast.makeText(requireActivity(),"Login Failed",Toast.LENGTH_SHORT).show()
                Log.d("login", "testing $it")
            }
        }
    }

    }

