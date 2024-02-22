package com.example.task9_designing.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.task9_designing.MainScreen
import com.example.task9_designing.ProjectConstants
import com.example.task9_designing.R
import com.example.task9_designing.databinding.FragmentQrFagmentBinding
import com.example.task9_designing.fragments.utilsclasses.DialogUtils
import com.example.task9_designing.fragments.utilsclasses.PermissionUtils


class QrFragment : Fragment() {

    private lateinit var binding: FragmentQrFagmentBinding
    private lateinit var permissionUtils: PermissionUtils
    private lateinit var dialogUtils: DialogUtils

    private val _binding get() = binding
    private lateinit var imageActivityResultLauncher: ActivityResultLauncher<Intent>

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                openCamera()
                Log.i("Permission: ", getString(R.string.granted))
            } else {
                Log.i("Permission: ", getString(R.string.denied))
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQrFagmentBinding.inflate(inflater, container, false)
        return _binding.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionUtils = PermissionUtils(requireActivity())
        dialogUtils = DialogUtils(requireActivity())
        imageActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Handle the result if needed
            }
        else {
            loadHomeFragment()
        }
        }
        if (permissionUtils.hasCameraPermission(requireActivity())) {
            openCamera()
        } else {
            requestCameraPermission()
        }


    }

    private fun loadHomeFragment() {
        val mainScreen = activity as? MainScreen
        mainScreen?.selectHomeIcon()

        val homeFragment = HomeFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, homeFragment)
            .commit()
    }

    private fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), Manifest.permission.CAMERA))
        {
           dialogUtils.requestCameraPermissionDialog(requireActivity())
        } else {
            requestPermissionLauncher.launch(
                Manifest.permission.CAMERA
            )
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.CAMERA),
                ProjectConstants.CAMERA_PERMISSION_CODE
            )

        }

    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        imageActivityResultLauncher.launch(cameraIntent)
        Log.d("Camera", "openCamera")


    }
}