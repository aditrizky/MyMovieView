package com.binar.mymovieview.ui.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.mymovieview.data.local.userauth.User
import com.binar.mymovieview.databinding.FragmentRegisterBinding
import com.binar.mymovieview.ui.ViewModelFactory


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding get() = _binding!!
    lateinit var registerViewModel : RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = ViewModelFactory(view.context)
        registerViewModel=ViewModelProvider(requireActivity(),factory)[RegisterViewModel::class.java]
        binding.registerButton.setOnClickListener {
            val confPassword= binding.confpasswordEditText.text.toString()
            val objectuser= User(
                username = binding.usernameEditTextText.text.toString(),
                email = binding.emailEditTextText.text.toString(),
                password = binding.passwordEditText.text.toString()
            )
            registerViewModel.addUser(objectuser,confPassword)
        }
        navigateUi()
    }
    private fun navigateUi(){
        registerViewModel.result().observe(viewLifecycleOwner){
            if(it == true){
                Toast.makeText(requireActivity(),"Register Success",Toast.LENGTH_SHORT).show()
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                Log.d("register", "testing berhasil $it")
                registerViewModel.reset()
            }else{
                Toast.makeText(requireActivity(),"Register Failed",Toast.LENGTH_SHORT).show()
                Log.d("register", "testing $it")
            }
        }
    }
}