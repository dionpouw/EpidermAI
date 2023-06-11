package com.jeflette.epidermai.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommonItem(
    val id: Int,
    val image: String,
    val name: String,
) : Parcelable
