package com.example.task9_designing.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.task9_designing.MainScreen
import com.example.task9_designing.R
import com.example.task9_designing.databinding.FragmentProfileBinding
import com.example.task9_designing.databinding.FragmentPromotionBinding


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private var customToolbar: Toolbar? = null
    private var backButton: ImageButton? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializingVariables()
        clickListeners()

    }

    private fun initializingVariables() {
        customToolbar = view?.findViewById(R.id.custom_toolbar_profile)
        backButton = view?.findViewById(R.id.backButtonProfile)
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

        val notificationsSwitch = binding.notificationsSwitch
        notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                notificationsSwitch.thumbTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.green1)
                )
                notificationsSwitch.trackTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.light_green)
                )
            } else {

                notificationsSwitch.thumbTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )
                notificationsSwitch.trackTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.grey)
                )
            }
        }
        val fingerPrintSwitch = binding.fingerPrintSwitch
        fingerPrintSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                fingerPrintSwitch.thumbTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.green1)
                )
                fingerPrintSwitch.trackTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.light_green)
                )
            } else {

                fingerPrintSwitch.thumbTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.white)
                )
                fingerPrintSwitch.trackTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(requireContext(), R.color.grey)
                )
            }
        }


    }


}