package com.jeflette.epidermai.data.remote.responses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.jeflette.epidermai.data.local.model.Medicine

@Parcelize
data class MedicinesResponse(

	@field:SerializedName("data")
	val data: List<Medicine>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable