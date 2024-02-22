package com.example.task9_designing

import android.animation.ValueAnimator
import android.app.ActionBar
import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.core.view.doOnLayout
import androidx.fragment.app.Fragment
import com.example.task9_designing.databinding.ActivityMainScreenBinding
import com.example.task9_designing.fragments.HomeFragment
import com.example.task9_designing.fragments.LocationFragment
import com.example.task9_designing.fragments.ProfileFragment
import com.example.task9_designing.fragments.PromotionFragment
import com.example.task9_designing.fragments.QrFragment

class MainScreen : AppCompatActivity() {
    private lateinit var binding: ActivityMainScreenBinding
    private lateinit var indicatorBar: View
    private lateinit var icons: List<ImageView>
    private val iconPercentageArray = floatArrayOf(0.027f, 0.225f, 0.425f, 0.625f, 0.825f)
    private val defaultIcons = listOf(
        R.drawable.ic_home_outlined,
        R.drawable.ic_location_outlined,
        R.drawable.qr_button,
        R.drawable.ic_promotion_outlined,
        R.drawable.ic_profile_outlined
    )
    private val selectedIcons = listOf(
        R.drawable.ic_home_filled,
        R.drawable.ic_location,
        R.drawable.qr_button,
        R.drawable.ic_promotion,
        R.drawable.ic_profile
    )
//    private var greenColor = Resources.getSystem().getColor(R.string.alarm_notification)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializingBinding()
        initializingVariables()
        clickListeners()
    }
    private fun initializingVariables() {
        loadFragment(HomeFragment())
        indicatorBar = binding.indicatorBar
        icons = listOf(
            binding.homeIcon,
            binding.locationIcon,
            binding.qrIcon,
            binding.promotionIcon,
            binding.profileIcon
        )
    }
    fun selectHomeIcon() {
        selectItem(binding.homeIcon, binding.homeText)
    }
    private fun clickListeners() {

        binding.homeIcon.setColorFilter(Color.parseColor("#50C878"), PorterDuff.Mode.SRC_IN)
        binding.homeText.setTextColor(Color.parseColor("#50C878"))
        binding.homeIcon.setImageResource(R.drawable.ic_home_filled)

        binding.homeLayout.setOnClickListener { selectItem(binding.homeIcon, binding.homeText) }
        binding.locationLayout.setOnClickListener { selectItem(binding.locationIcon, binding.locationText) }
        binding.qrLayout.setOnClickListener { selectItem(binding.qrIcon, binding.qrText) }
        binding.promotionLayout.setOnClickListener { selectItem(binding.promotionIcon, binding.promotionText) }
        binding.profileLayout.setOnClickListener { selectItem(binding.profileIcon, binding.profileText) }
    }
    private fun selectItem(selectedItem: ImageView, selectedText: TextView) {
        resetIcons()
        val selectedIndex = icons.indexOf(selectedItem)
        val isQRButton = selectedIndex == 2
        if (!isQRButton) {
            selectedItem.setColorFilter(Color.parseColor("#50C878"), PorterDuff.Mode.SRC_IN)
            selectedText.setTextColor(Color.parseColor("#50C878"))
        }

        val screenWidth = resources.displayMetrics.widthPixels
        val targetX = screenWidth * iconPercentageArray[selectedIndex]
        val layoutParams = indicatorBar.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.startToStart = selectedItem.id
        layoutParams.endToEnd = selectedItem.id
        indicatorBar.layoutParams = layoutParams
        indicatorBar.animate().x(targetX).setDuration(ProjectConstants.BOTTOM_NAV_BAR_DURATION.toLong()).start()

        loadFragment(getFragment(selectedIndex))
        selectedItem.setImageResource(selectedIcons[selectedIndex])

    }
    private fun getFragment(index: Int): Fragment {
        return when (index) {
            0 -> HomeFragment()
            1 -> LocationFragment()
            2 -> QrFragment()
            3 -> PromotionFragment()
            4 -> ProfileFragment()
            else -> HomeFragment()
        }
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun resetIcons() {
        icons.forEachIndexed { index, icon ->
            icon.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
            val textView = when (index) {
                0 -> binding.homeText
                1 -> binding.locationText
                3 -> binding.promotionText
                4 -> binding.profileText
                else -> binding.profileText
            }
            textView.setTextColor(Color.GRAY)
            icon.setImageResource(defaultIcons[index])
        }
        binding.qrIcon.setColorFilter(Color.parseColor("#50C878"), PorterDuff.Mode.SRC_IN)
        binding.qrText.setTextColor(Color.parseColor("#50C878"))
    }

    private fun initializingBinding() {
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}