package com.binar.mymovieview.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.binar.mymovieview.mvvm.UserAuthViewModel
import com.binar.mymovieview.data.User
import com.binar.mymovieview.databinding.FragmentRegisterBinding
import com.binar.mymovieview.room.AplicationDB


class RegisterFragment : Fragment() {
    private var mDB: AplicationDB? = null
    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding get() = _binding!!
    private val viewModel : UserAuthViewModel by activityViewModels()
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
        mDB = AplicationDB.getInstance(requireContext())
        binding.registerButton.setOnClickListener {
            val confPassword= binding.confpasswordEditText.text.toString()
            val objectuser= User(
                username = binding.usernameEditTextText.text.toString(),
                email = binding.emailEditTextText.text.toString(),
                password = binding.passwordEditText.text.toString()
            )
            viewModel.registerUser(objectuser,confPassword)
        }
        navigateUi()
    }
    private fun navigateUi(){
        viewModel.registervalidation.observe(viewLifecycleOwner){
            if(it != 0){
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                Log.d("register", "testing berhasil $it")
            }else{
                Log.d("register", "testing $it")
            }
        }
    }
}