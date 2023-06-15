package com.jeflette.epidermai.data.remote

import com.jeflette.epidermai.network.ApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAllDiseases() = apiService.getAllDiseases()

    suspend fun getAllMedicines() = apiService.getAllMedicines()

    suspend fun getMedicineDetail(name: String) = apiService.getMedicineDetail(name)

    suspend fun getDiseaseDetail(name: String) = apiService.getDiseaseDetail(name)

    suspend fun getPredict(name: String) = apiService.getPredict(name)
}