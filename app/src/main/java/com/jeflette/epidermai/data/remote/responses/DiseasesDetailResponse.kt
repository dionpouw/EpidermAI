package com.jeflette.epidermai.data.remote.responses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DiseasesDetailResponse(

	@field:SerializedName("data")
	val data: DataDiseasesDetail? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable

@Parcelize
data class DataDiseasesDetail(

	@field:SerializedName("dss_desc")
	val dssDesc: String? = null,

	@field:SerializedName("dss_img")
	val dssImg: String? = null,

	@field:SerializedName("dss_id")
	val dssId: Int? = null,

	@field:SerializedName("medicine_details")
	val medicineDetails: List<MedicineDetailsItem?>? = null,

	@field:SerializedName("dss_name")
	val dssName: String? = null
) : Parcelable

@Parcelize
data class MedicineDetailsItem(

	@field:SerializedName("mdc_name")
	val mdcName: String? = null,

	@field:SerializedName("mdc_img")
	val mdcImg: String? = null,

	@field:SerializedName("mdc_desc")
	val mdcDesc: String? = null
) : Parcelable
