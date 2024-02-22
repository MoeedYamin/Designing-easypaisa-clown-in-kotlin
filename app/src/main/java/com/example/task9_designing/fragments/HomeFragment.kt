package com.example.task9_designing.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.task9_designing.MainScreen
import com.example.task9_designing.R
import com.example.task9_designing.databinding.FragmentHomeBinding
import com.example.task9_designing.fragments.viewpager.ViewPagerAdapter
import com.example.task9_designing.fragments.viewpager.ViewPagerAdapterSecond
import com.google.android.material.navigation.NavigationView

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var viewPager2: ViewPager2
    private lateinit var indicator1: ImageView
    private lateinit var indicator2: ImageView
    private lateinit var indicator21: ImageView
    private lateinit var indicator22: ImageView
    private lateinit var indicator23: ImageView
    private lateinit var indicator24: ImageView
    private var imageButton: ImageButton? = null
    private var currentPage = 0
    private var currentPage2 = 0
    private var customToolbar: androidx.appcompat.widget.Toolbar? = null
    private var backButton: ImageButton? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializingVariables()
        setupViewPager()
        clickListeners()
        setup2ndViewPager()
        callBackListeners()

    }

    private fun clickListeners() {
        imageButton?.setOnClickListener {
            val drawerLayout: DrawerLayout? = view?.findViewById(R.id.drawer_layout)
            drawerLayout?.openDrawer(GravityCompat.START)
        }

        val navigationView: NavigationView? = view?.findViewById(R.id.navigation_view)
        navigationView?.setNavigationItemSelectedListener { menuItem ->

            when (menuItem.itemId) {

                else -> false
            }
        }
        backButton?.setOnClickListener(View.OnClickListener {
            val mainScreen = activity as? MainScreen
            mainScreen?.selectHomeIcon()
            val homeFragment = HomeFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, homeFragment)
                .commit()
        })
    }

    private fun setup2ndViewPager() {
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.adapter = ViewPagerAdapterSecond()

        viewPager2.setCurrentItem(0, false)

        updateIndicatorsFor2ndViewPager()

    }

    private fun callBackListeners() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPage = position
                updateIndicators()
            }
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                currentPage2 = position
                updateIndicatorsFor2ndViewPager()
            }
        })

    }

    private fun updateIndicatorsFor2ndViewPager() {
        when (currentPage2) {
            0 -> {
                indicator21.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot_selected)
                indicator22.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot)
                indicator23.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot)
                indicator24.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot)
            }
            1 -> {
                indicator21.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot)
                indicator22.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot_selected)
                indicator23.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot)
                indicator24.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot)
            }
            2 -> {
                indicator21.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot)
                indicator22.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot)
                indicator23.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot_selected)
                indicator24.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot)
            }
            3 -> {
                indicator21.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot)
                indicator22.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot)
                indicator23.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot)
                indicator24.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot_selected)
            }
        }

    }

    private fun setupViewPager() {
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager.adapter = ViewPagerAdapter()

        viewPager.setCurrentItem(0, false)

        updateIndicators()
    }

    private fun updateIndicators()
    {
        when (currentPage) {
            0 -> {
                indicator1.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot_selected)
                indicator2.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot)
            }
            1 -> {
                indicator1.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot)
                indicator2.setImageResource(R.drawable.ic_home_more_with_easypaisa_scroll_dot_selected)
            }
        }

    }
    private fun initializingVariables() {
        imageButton = view?.findViewById(R.id.drawerButton)
        customToolbar = view?.findViewById(R.id.custom_toolbar_profile)
        backButton = view?.findViewById(R.id.backButtonProfile)
        viewPager = binding.homeFirstViewPager
        viewPager2= binding.homeSecondViewPager
        indicator1 = binding.homeFirstViewPagerIndicator1
        indicator2 = binding.homeFirstViewPagerIndicator2
        indicator21 = binding.homeSecondViewPagerIndicator1
        indicator22 = binding.homeSecondViewPagerIndicator2
        indicator23 = binding.homeSecondViewPagerIndicator3
        indicator24 = binding.homeSecondViewPagerIndicator4
    }

}