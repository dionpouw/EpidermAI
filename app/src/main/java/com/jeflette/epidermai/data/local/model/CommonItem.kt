package com.jeflette.epidermai.data.local.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommonItem(
    val id: Int,
    val image: String,
    val name: String,
    val desc: String,
) : Parcelable
