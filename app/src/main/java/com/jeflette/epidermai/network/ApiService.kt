package com.jeflette.epidermai.network

import com.jeflette.epidermai.data.remote.responses.DiseasesDetailResponse
import com.jeflette.epidermai.data.remote.responses.DiseasesResponse
import com.jeflette.epidermai.data.remote.responses.MedicinesDetailResponse
import com.jeflette.epidermai.data.remote.responses.MedicinesResponse
import com.jeflette.epidermai.data.remote.responses.PredictionResponses
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("diseases")
    suspend fun getAllDiseases(): Response<DiseasesResponse>

    @GET("medicines")
    suspend fun getAllMedicines(): Response<MedicinesResponse>

    @GET("medicines/{name}")
    suspend fun getMedicineDetail(@Path("name") name: String): Response<MedicinesDetailResponse>

    @GET("diseases/{name}")
    suspend fun getDiseaseDetail(@Path("name") name: String): Response<DiseasesDetailResponse>

    @GET("predict/{name}")
    suspend fun getPredict(@Path("name") name: String): Response<PredictionResponses>


}