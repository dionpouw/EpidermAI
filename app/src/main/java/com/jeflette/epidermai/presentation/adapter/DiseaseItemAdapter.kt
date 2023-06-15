package com.jeflette.epidermai.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeflette.epidermai.R
import com.jeflette.epidermai.data.local.model.Disease
import com.jeflette.epidermai.databinding.DiseaseItemBinding
import com.jeflette.epidermai.presentation.disease.DiseaseFragmentDirections
import com.jeflette.epidermai.util.safeNavigate

class DiseaseItemAdapter : RecyclerView.Adapter<DiseaseItemAdapter.ViewHolder>() {

    private val diseaseList = mutableListOf<Disease>()

    fun addDiseaseList(list: List<Disease>) {
        this.diseaseList.clear()
        diseaseList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: DiseaseItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun binding(disease: Disease) {
            Glide.with(itemView.context).load(disease.dssImg).into(itemBinding.diseaseImage)
            itemBinding.diseaseName.text = disease.dssName
            itemBinding.diseaseDesc.text = disease.dssDesc
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        DiseaseItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount(): Int = diseaseList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(diseaseList[position])
        holder.itemView.setOnClickListener {
            Navigation.findNavController(it).currentDestination?.id.let { id ->
                when (id) {
                    R.id.diseaseFragment -> {
                        Navigation.findNavController(it).safeNavigate(
                            DiseaseFragmentDirections.actionDiseaseFragmentToDiseaseDetailFragment(
                                diseaseList[position].dssName
                            )
                        )
                    }
                }
            }
        }
    }
}