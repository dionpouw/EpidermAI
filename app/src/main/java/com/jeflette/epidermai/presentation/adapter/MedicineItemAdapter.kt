package com.jeflette.epidermai.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jeflette.epidermai.databinding.MedicineItemBinding
import com.jeflette.epidermai.model.Medicine

class MedicineItemAdapter : RecyclerView.Adapter<MedicineItemAdapter.ViewHolder>() {

    private val medicineList = mutableListOf<Medicine>()

    fun addMedicineList(list: List<Medicine>) {
        this.medicineList.clear()
        medicineList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: MedicineItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun binding(medicine: Medicine) {
            Glide.with(itemView.context).load(medicine.image).into(itemBinding.medicineImage)
            itemBinding.medicineName.text = medicine.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        MedicineItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )


    override fun getItemCount(): Int = medicineList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(medicineList[position])
    }
}