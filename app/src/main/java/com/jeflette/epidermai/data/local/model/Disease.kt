package com.jeflette.epidermai.data.local.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Disease(

    @field:SerializedName("dss_id") val dssId: Int,

    @field:SerializedName("dss_img") val dssImg: String,
    @field:SerializedName("dss_name") val dssName: String,

    @field:SerializedName("dss_desc") val dssDesc: String
) : Parcelable
