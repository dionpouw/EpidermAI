package com.jeflette.epidermai.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Disease(
    val diseaseItem: CommonItem,
    val description: String,
    val listMedicineItem: List<CommonItem>,
) : Parcelable
