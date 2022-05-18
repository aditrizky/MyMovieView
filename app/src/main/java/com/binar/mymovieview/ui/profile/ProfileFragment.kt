package com.binar.mymovieview.ui.profile

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.binar.mymovieview.R
import com.binar.mymovieview.data.local.userauth.User
import com.binar.mymovieview.databinding.FragmentProfileBinding
import com.binar.mymovieview.ui.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding get() = _binding!!
    lateinit var viewModel: ProfileViewModel
    private var usernameValue = "default"
    var date :Any?=null
    var imagepath :String?=null
    var bitmap: Bitmap?=null

    private val REQUEST_CODE_PERMISSION = 100

    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleCameraImage(result.data)
            }

        }
    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            imagepath=result.toString()
            binding.profileImageView.setImageURI(imagepath!!.toUri())
            Log.d("tesingg",result.toString())

        }

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
        val factory = ViewModelFactory(view.context)
        viewModel= ViewModelProvider(requireActivity(),factory)[ProfileViewModel::class.java]
        setUsername()
        setField()
        binding.logoutButton.setOnClickListener {
        logOut()
        }

        //edit text listerner
        binding.textmaterial.setEndIconOnClickListener{
        calendarCreate()
        }

        binding.updateButton.setOnClickListener {
            update()
        }
        binding.profileImageView.setOnClickListener {
            checkingPermissions()
        }
    }
    private fun setUsername(){
        viewModel.getUsername().observe(viewLifecycleOwner){
            usernameValue=it
            viewModel.getAllData(it)
        }
    }
    private fun setField(){
        viewModel.resultUser().observe(viewLifecycleOwner){
            Log.d("usernamecakk",it.toString())
            if (it.username !="null"){
                binding.usernameEditTextText.setText(it.username.toString())
            }
            if (it.fullName != "null"){
                binding.fullnamelEditTextText.setText(it.fullName)
            }
            if (it.birthDate != "null"){
                date=it.birthDate
                binding.birthDateEditText.setText(it.birthDate.toString())
            }
            if (it.address != "null"){
                binding.addressEditText.setText(it.address.toString())
            }
            if (imagepath == null) {
                if (it.imagePath != "null") {
                    imagepath = it.imagePath
                    Glide.with(requireActivity())
                        .load(imagepath)
                        .apply(RequestOptions.centerCropTransform())
                        .error(R.drawable.ic_launcher_background)
                        .into(binding.profileImageView)

                    Log.d("tesing", it.imagePath.toString())
                }
            }
        }


    }

    private fun calendarCreate(){
        //calender
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day =calender.get(Calendar.DAY_OF_MONTH)
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

    private fun logOut(){
        AlertDialog.Builder(context).setPositiveButton("Yes") { _, _ ->
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

    private fun update(){
        val objectUser = User(
            username = binding.usernameEditTextText.text.toString(),
            fullName = binding.fullnamelEditTextText.text.toString(),
            birthDate = date.toString(),
            address = binding.addressEditText.text.toString(),
            imagePath = imagepath
        )


        viewModel.getEmail().observe(viewLifecycleOwner){
            viewModel.updateData(objectUser,it)
        }
        viewModel.result().observe(viewLifecycleOwner){
            if(it==true){
                viewModel.setUsername(objectUser.username.toString())
                viewModel.getAllData(objectUser.username.toString())
                Toast.makeText(requireActivity(),"Update Success", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireActivity(),"Update Failed", Toast.LENGTH_SHORT).show()
            }
        }
        setField()
    }



    private fun checkingPermissions() {
        if (isGranted(
                requireActivity(),
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION,
            )
        ) {
            chooseImageDialog()
        }
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
       AlertDialog.Builder(requireActivity())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", activity?.packageName,null )
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun chooseImageDialog() {
        AlertDialog.Builder(requireActivity())
            .setMessage("choose Image")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }

    private fun openGallery() {
        activity?.intent?.type = "image/*"
        galleryResult.launch("image/*")
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }

    private fun handleCameraImage(intent: Intent?) {
       bitmap = intent?.extras?.get("data") as Bitmap
        val result = viewModel.bitmapToUri(bitmap!!)
        imagepath=result.toString()
        binding.profileImageView.setImageURI(imagepath!!.toUri())
        Log.d("tesinggg",bitmap.toString())
    }


}