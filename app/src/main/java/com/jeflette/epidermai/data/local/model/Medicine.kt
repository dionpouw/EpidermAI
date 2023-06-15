package com.jeflette.epidermai.data.local.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Medicine(

    @field:SerializedName("mdc_id") val mdcId: Int,

    @field:SerializedName("mdc_img") val mdcImg: String,

    @field:SerializedName("mdc_name") val mdcName: String,

    @field:SerializedName("mdc_desc") val mdcDesc: String
) : Parcelable
