package com.jeflette.epidermai.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeflette.epidermai.data.local.model.CommonItem
import com.jeflette.epidermai.databinding.CardItemBinding
import com.jeflette.epidermai.presentation.home.HomeFragmentDirections

class BlogCardAdapter : RecyclerView.Adapter<BlogCardAdapter.ViewHolder>() {

    private val commonItemList = mutableListOf<CommonItem>()

    fun addCardItemList(list: List<CommonItem>) {
        this.commonItemList.clear()
        commonItemList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: CardItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun binding(commonItem: CommonItem) {
            Glide.with(itemView.context).load(commonItem.image).into(itemBinding.cardImage)
            itemBinding.cardName.text = commonItem.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        CardItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )


    override fun getItemCount(): Int = commonItemList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(commonItemList[position])
        holder.itemView.setOnClickListener {
            val actionHomeFragmentToBlogFragment =
                HomeFragmentDirections.actionHomeFragmentToBlogFragment(commonItemList[position])
            holder.itemView.findNavController().navigate(actionHomeFragmentToBlogFragment)
        }
    }
}
