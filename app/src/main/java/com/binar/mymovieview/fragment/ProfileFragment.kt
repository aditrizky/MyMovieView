package com.binar.mymovieview.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.binar.mymovieview.data.User
import com.binar.mymovieview.databinding.FragmentProfileBinding
import com.binar.mymovieview.mvvm.UserAuthViewModel
import java.util.*

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!
    private val viewModel : UserAuthViewModel by activityViewModels()
    private var usernameValue = "default"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setField()
        binding.logoutButton.setOnClickListener {
            AlertDialog.Builder(it.context).setPositiveButton("Yes") { _, _ ->
                viewModel.logout()
                findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToLoginFragment())
            }
                .setNegativeButton(
                    "No"
                ) { p0, _ ->
                    p0.dismiss()
                }
                .setMessage("Logout From $usernameValue ?").setTitle("Confirm Logout")
                .create().show()
        }
        //calender
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day =calender.get(Calendar.DAY_OF_MONTH)
        var date :Any?=null
        //edit text listerner

        binding.textmaterial.setEndIconOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val calendarShow =  DatePickerDialog(requireActivity(), {
                        view, mYear:Int, mMonth: Int, mDay: Int ->
                   binding.birthDateEditText.setText(mDay.toString()+"-"+(mMonth+1).toString()+"-"+mYear.toString())
                    Log.d("profile",month.toString())
                    date = mDay.toString()+"-"+(mMonth+1).toString()+"-"+mYear.toString()
                },year,month,day)
               calendarShow.show()
            } else {
               date=binding.birthDateEditText.text.toString()
            }
        }

        binding.updateButton.setOnClickListener {
        val objectUser = User(
            username = binding.usernameEditTextText.text.toString(),
            fullName = binding.fullnamelEditTextText.text.toString(),
            birthDate = date.toString(),
            address = binding.addressEditText.text.toString()
        )
            viewModel.updateUserData(objectUser)
            setField()
        }

    }
    private fun setField(){
        viewModel.username.observe(viewLifecycleOwner){
            if (it!="null"){
                binding.usernameEditTextText.setText(it)
                usernameValue=it
            }
        }
        viewModel.fullname.observe(viewLifecycleOwner){
            if (it!="null"){
                binding.fullnamelEditTextText.setText(it)
            }
        }
        viewModel.birth.observe(viewLifecycleOwner){
            if (it!="null"){
                binding.birthDateEditText.setText(it)
            }
        }
        viewModel.address.observe(viewLifecycleOwner){
            if (it!="null"){
                binding.addressEditText.setText(it)
            }
        }
    }


}