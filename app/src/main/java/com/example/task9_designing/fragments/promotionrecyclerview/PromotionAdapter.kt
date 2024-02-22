package com.example.task9_designing.fragments.promotionrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task9_designing.databinding.ItemNodesBinding

class PromotionAdapter(private val promotionItems: ArrayList<PromotionItem>) :
    RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder>() {


    class PromotionViewHolder(val binding: ItemNodesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(promotionItem: PromotionItem) {
            binding.promotionItem = promotionItem
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PromotionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNodesBinding.inflate(layoutInflater, parent, false)
        return PromotionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PromotionViewHolder, position: Int) {
        val currentItem = promotionItems[position]
        holder.bind(currentItem)
        holder.binding.imageViewPromotion.setImageResource(currentItem.imageResource)
        holder.binding.textViewTitle.text = currentItem.title
        holder.binding.textViewDescription.text = currentItem.description
    }

    override fun getItemCount(): Int {
        return promotionItems.size
    }
    fun setData(data: List<PromotionItem>) {
        promotionItems.clear()
        promotionItems.addAll(data)
        notifyDataSetChanged()
    }
}