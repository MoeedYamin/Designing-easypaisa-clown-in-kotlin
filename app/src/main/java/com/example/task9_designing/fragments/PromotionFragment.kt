package com.example.task9_designing.fragments

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task9_designing.MainScreen
import com.example.task9_designing.R
import com.example.task9_designing.databinding.FragmentPromotionBinding
import com.example.task9_designing.fragments.promotionrecyclerview.PromotionAdapter
import com.example.task9_designing.fragments.promotionrecyclerview.PromotionItem


class PromotionFragment : Fragment() {
    private lateinit var binding: FragmentPromotionBinding
    private lateinit var promotionAdapter: PromotionAdapter
    private var customToolbar: Toolbar? = null
    private var backButton: ImageButton? = null
    private var toolBarTitle: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPromotionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializingVariables()

        clickListeners()
        setupRecyclerView()
        populatePromotionItems()
    }

    private fun initializingVariables() {
        customToolbar = view?.findViewById(R.id.custom_toolbar_promotion_and_cashpoints)
        backButton = view?.findViewById(R.id.backButton)
        toolBarTitle = view?.findViewById(R.id.toolbarTitle)
//        toolbarTitle?.text= Resources.getSystem().getString(R.string.promotion)
        toolBarTitle?.text= getString(R.string.promotion)

    }

    private fun clickListeners() {
        backButton?.setOnClickListener {
            val mainScreen = activity as? MainScreen
            mainScreen?.selectHomeIcon()
            val homeFragment = HomeFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, homeFragment)
                .commit()
        }
    }


    private fun populatePromotionItems() {
        val promotionItems = listOf(
            PromotionItem(R.drawable.p1, getString(R.string.lucky_draw), getString(R.string.on11)),
            PromotionItem(R.drawable.p2, getString(R.string.lucky_draw_here), getString(R.string.win_amazing_prizes)),
            PromotionItem(R.drawable.p3, getString(R.string.daraz11_sale), getString(R.string.earn_massive_discounts)),
            PromotionItem(R.drawable.p4, getString(R.string.chance_to_win_a_car), getString(R.string.win_honda_civic)),
            PromotionItem(R.drawable.p5, getString(R.string.sport_car_in_pak), getString(R.string.our_very_1st_sports_cars))
        )
        promotionAdapter.setData(promotionItems)

    }

    private fun setupRecyclerView() {
        promotionAdapter = PromotionAdapter(ArrayList())
        binding.recyclerViewPromotion.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = promotionAdapter
        }
    }


}