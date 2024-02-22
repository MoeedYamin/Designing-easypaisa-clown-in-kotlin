package com.example.task9_designing.fragments.viewpager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task9_designing.R

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflating different layouts for the first and second pages
        return when (viewType) {
            PAGE_ONE -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.viewpager_icons,
                    parent,
                    false
                )
                ViewHolder(view)
            }
            PAGE_TWO -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.viewpager_icons_page_2,
                    parent,
                    false
                )
                ViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Nothing to bind here
    }

    override fun getItemCount(): Int {
        return NUM_PAGES // Set the total number of pages
    }

    override fun getItemViewType(position: Int): Int {
        // Returning different view types for different pages
        return when (position) {
            0 -> PAGE_ONE
            1 -> PAGE_TWO
            else -> throw IllegalArgumentException("Invalid position")
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        private const val PAGE_ONE = 0
        private const val PAGE_TWO = 1
        private const val NUM_PAGES = 2 // Total number of pages
    }
}