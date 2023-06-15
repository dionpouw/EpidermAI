package com.jeflette.epidermai.data.remote.responses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class MedicinesDetailResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("mdc_id")
	val mdcId: Int? = null,

	@field:SerializedName("mdc_name")
	val mdcName: String? = null,

	@field:SerializedName("mdc_img")
	val mdcImg: String? = null,

	@field:SerializedName("mdc_desc")
	val mdcDesc: String? = null
) : Parcelable
