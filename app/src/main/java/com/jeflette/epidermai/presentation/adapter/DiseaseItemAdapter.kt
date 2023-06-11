package com.jeflette.epidermai.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeflette.epidermai.databinding.DiseaseItemBinding
import com.jeflette.epidermai.model.Disease
import com.jeflette.epidermai.presentation.disease.DiseaseFragmentDirections

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
            Glide.with(itemView.context).load(disease.diseaseItem.image)
                .into(itemBinding.diseaseImage)
            itemBinding.diseaseName.text = disease.diseaseItem.name
            itemBinding.diseaseDesc.text = disease.description
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
            val actionDiseaseFragmentToDiseaseDetailFragment =
                DiseaseFragmentDirections.actionDiseaseFragmentToDiseaseDetailFragment(diseaseList[position].diseaseItem.name)
        }
    }
}
