package com.jeflette.epidermai.data.remote.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.jeflette.epidermai.data.local.model.Medicine
import kotlinx.parcelize.Parcelize

@Parcelize
data class PredictionResponses(

    @field:SerializedName("data") val data: PredictionData? = null,

    @field:SerializedName("message") val message: String? = null,

    @field:SerializedName("status") val status: Int? = null
) : Parcelable

@Parcelize
data class PredictionData(

    @field:SerializedName("dss_desc") val dssDesc: String? = null,

    @field:SerializedName("dss_img") val dssImg: String? = null,

    @field:SerializedName("dss_id") val dssId: Int? = null,

    @field:SerializedName("medicine_details") val medicineDetails: List<Medicine?>? = null,

    @field:SerializedName("dss_name") val dssName: String? = null
) : Parcelable
