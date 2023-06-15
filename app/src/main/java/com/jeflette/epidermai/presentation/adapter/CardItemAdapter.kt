package com.jeflette.epidermai.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeflette.epidermai.R
import com.jeflette.epidermai.data.local.model.CommonItem
import com.jeflette.epidermai.data.remote.responses.DiseasesDetailResponse
import com.jeflette.epidermai.databinding.CardItemBinding
import com.jeflette.epidermai.presentation.diseasedetail.DiseaseDetailFragment
import com.jeflette.epidermai.presentation.diseasedetail.DiseaseDetailFragmentDirections
import com.jeflette.epidermai.presentation.home.HomeFragmentDirections
import com.jeflette.epidermai.presentation.medicine.MedicineFragmentDirections
import com.jeflette.epidermai.util.safeNavigate
import java.util.Locale

class CardItemAdapter : RecyclerView.Adapter<CardItemAdapter.ViewHolder>() {

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
            findNavController(it).currentDestination?.id.let { id ->
                when (id) {
                    R.id.homeFragment -> {
                        findNavController(it).safeNavigate(
                            HomeFragmentDirections.actionHomeFragmentToDiseaseDetailFragment(
                                commonItemList[position].name.lowercase(Locale.ROOT)
                            )
                        )
                    }
                    R.id.medicineFragment -> {
                        findNavController(it).safeNavigate(
                            MedicineFragmentDirections.actionMedicineFragmentToMedicineDetailFragment(
                                commonItemList[position].name.lowercase(Locale.ROOT)
                            )
                        )
                    }
                    R.id.diseaseDetailFragment ->{
                        findNavController(it).safeNavigate(
                            DiseaseDetailFragmentDirections.actionDiseaseDetailFragmentToMedicineDetailFragment(
                                commonItemList[position].name.lowercase(Locale.ROOT)
                            )
                        )
                    }
                }
            }
        }
    }
}
