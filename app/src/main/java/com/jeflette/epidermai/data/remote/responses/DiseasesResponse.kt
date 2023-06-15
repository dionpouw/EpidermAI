package com.jeflette.epidermai.data.remote.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.jeflette.epidermai.data.local.model.Disease
import kotlinx.parcelize.Parcelize

@Parcelize
data class DiseasesResponse(

    @field:SerializedName("data") val data: List<Disease>? = null,

    @field:SerializedName("message") val message: String? = null,

    @field:SerializedName("status") val status: Int? = null
) : Parcelable