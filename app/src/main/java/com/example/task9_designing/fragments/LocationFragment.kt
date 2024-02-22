package com.example.task9_designing.fragments

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.task9_designing.MainScreen
import com.example.task9_designing.ProjectConstants
import com.example.task9_designing.R
import com.example.task9_designing.databinding.FragmentLocationBinding
import com.example.task9_designing.fragments.utilsclasses.DialogUtils
import com.example.task9_designing.fragments.utilsclasses.PermissionUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng


class LocationFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentLocationBinding
    private var customToolbar: Toolbar? = null
    private var backButton: ImageButton? = null
    private var toolBarTitle: TextView? = null
    private lateinit var permissionUtils: PermissionUtils
    private lateinit var dialogUtils: DialogUtils
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    private var isBottomSheetVisible = true
    private var cashDepositBtnSwitch1 = true
    private var cashDepositBtnSwitch2 = true
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                binding.blackOverlayLayout.visibility = View.GONE
                setupGoogleMap()
                Log.i("Permission: ", getString(R.string.granted))
            } else {
                Log.i("Permission: ", getString(R.string.denied))
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializingVariables()
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()
        binding.mapView.getMapAsync(this)


        if (permissionUtils.hasLocationPermission(requireActivity())) {
            binding.blackOverlayLayout.visibility = View.GONE
            if(permissionUtils.hasCourserPermission(requireActivity()))
            {
                binding.blackOverlayLayout.visibility = View.GONE
                setupGoogleMap()
            }
            else
            {
                binding.blackOverlayLayout.visibility = View.VISIBLE
                requestCursorPermission()
            }

        } else {
            binding.blackOverlayLayout.visibility = View.VISIBLE
            requestLocationPermission()
        }
        clickListeners()
    }

    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        googleMap.uiSettings.isMyLocationButtonEnabled = true
    }
    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }
    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    private fun fetchCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                val currentLatLng = LatLng(it.latitude, it.longitude)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            }
        }

    }

    private fun requestCursorPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION))
        {
            dialogUtils.requestCursorPermissionDialog(requireActivity())

        } else {
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                ProjectConstants.LOCATION_PERMISSION_REQUEST_CODE
            )
        }

    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
        {
            dialogUtils.requestFinePermissionDialog(requireActivity())

        } else {
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                ProjectConstants.LOCATION_PERMISSION_REQUEST_CODE
            )
        }

    }

    private fun setupGoogleMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        mapFragment?.getMapAsync { map ->
            googleMap = map
            googleMap.setOnMapLoadedCallback {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    googleMap.uiSettings.isMyLocationButtonEnabled = true
                    googleMap.isMyLocationEnabled = true
                    fetchCurrentLocation()
                }
                googleMap.uiSettings.isMyLocationButtonEnabled = true
                googleMap.isMyLocationEnabled = true
                fetchCurrentLocation()

            }
        }
    }


    private fun clickListeners() {
        backButton?.setOnClickListener(View.OnClickListener {
            val mainScreen = activity as? MainScreen
            mainScreen?.selectHomeIcon()
            val homeFragment = HomeFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, homeFragment)
                .commit()
        })

        binding.buttonCurrentLocation.setOnClickListener {
            fetchCurrentLocation()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val floatValue = (progress / 10.0f)
                binding.textViewProgress.text = floatValue.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        binding.bottomSheetButton.setOnClickListener {
            if (isBottomSheetVisible) {
                hideBottomSheet()
            } else {
                showBottomSheet()
            }
        }

        binding.cashDepositCashPointBtn.setOnClickListener(View.OnClickListener {
            if(cashDepositBtnSwitch1==true)
            {
                binding.cashDepositCashPointBtn.setBackgroundResource(R.drawable.home_drawer_username_bg)
                cashDepositBtnSwitch1= false
            }
            else
            {
                binding.cashDepositCashPointBtn.setBackgroundResource(R.drawable.cash_points_cash_deposit_btn_grey_bg)
                cashDepositBtnSwitch1= true
            }

        })

        binding.marqueeText.setOnClickListener(View.OnClickListener {
            if(cashDepositBtnSwitch2==true)
            {
                binding.marqueeText.setBackgroundResource(R.drawable.home_drawer_username_bg)
                cashDepositBtnSwitch2= false
            }
            else
            {
                binding.marqueeText.setBackgroundResource(R.drawable.cash_points_cash_deposit_btn_grey_bg)
                cashDepositBtnSwitch2= true
            }

        })

        binding.blackOverlayLayout.setOnClickListener(View.OnClickListener {

        })

    }

    private fun showBottomSheet() {
        val cashDepositBarLayoutParams = binding.cashDepositBar.layoutParams as RelativeLayout.LayoutParams
        val slideUp = ObjectAnimator.ofFloat( binding.cashDepositBar, "translationY", 0f).apply {
            duration = ProjectConstants.BOTTOM_SHEET_DURATION_DURATION.toLong()
        }

        val fadeIn = ObjectAnimator.ofFloat( binding.bottomLinearLayout, "alpha", 0f, 1f).apply {
            duration = ProjectConstants.BOTTOM_SHEET_DURATION_DURATION.toLong()
        }

        val animSet = AnimatorSet()
        animSet.playTogether(slideUp, fadeIn)
        animSet.start()

        binding.bottomSheetButton.setImageResource(R.drawable.ic_cash_points_arrow_down)
        binding.cashDepositBar.setBackgroundResource(R.drawable.cash_points_cash_deposit_bar_bg)
        cashDepositBarLayoutParams.addRule(RelativeLayout.ABOVE, R.id.bottomLinearLayout)
        cashDepositBarLayoutParams.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        isBottomSheetVisible = true
    }



    private fun hideBottomSheet() {
        val cashDepositBarLayoutParams =  binding.cashDepositBar.layoutParams as RelativeLayout.LayoutParams
        val slideDown = ObjectAnimator.ofFloat(
            binding.cashDepositBar,
            "translationY",
            0f,
            resources.getDimension(R.dimen._250dp)
        ).apply {
            duration = ProjectConstants.BOTTOM_SHEET_DURATION_DURATION.toLong()
        }

        val fadeOut = ObjectAnimator.ofFloat( binding.bottomLinearLayout, "alpha", 1f, 0f).apply {
            duration = ProjectConstants.BOTTOM_SHEET_DURATION_DURATION.toLong()
        }


        val animSet = AnimatorSet()
        animSet.playTogether(slideDown, fadeOut)
        animSet.start()

        cashDepositBarLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        cashDepositBarLayoutParams.removeRule(RelativeLayout.ABOVE)

        binding.bottomSheetButton.setImageResource(R.drawable.ic_cash_points_arrow_up)
        binding.cashDepositBar.setBackgroundResource(R.drawable.cash_points_cash_deposit_bar_bg_down)
        isBottomSheetVisible = false
    }

    private fun initializingVariables() {

        permissionUtils = PermissionUtils(requireActivity())
        dialogUtils = DialogUtils(requireActivity())
        binding.marqueeText.ellipsize = TextUtils.TruncateAt.MARQUEE
        binding.marqueeText.isSelected = true
        customToolbar = view?.findViewById(R.id.custom_toolbar_promotion_and_cashpoints)
        backButton = view?.findViewById(R.id.backButton)
        toolBarTitle = view?.findViewById(R.id.toolbarTitle)
        toolBarTitle?.text= getString(R.string.cash_points)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
    }
}
