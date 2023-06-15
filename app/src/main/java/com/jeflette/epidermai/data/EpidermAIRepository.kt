package com.jeflette.epidermai.data

import com.jeflette.epidermai.data.remote.RemoteDataSource
import com.jeflette.epidermai.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class EpidermAIRepository
@Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {

    suspend fun getAllDiseases() = wrapWithFlow(remoteDataSource.getAllDiseases())

    suspend fun getAllMedicines() = wrapWithFlow(remoteDataSource.getAllMedicines())

    suspend fun getMedicineDetail(name: String) =
        wrapWithFlow(remoteDataSource.getMedicineDetail(name))

    suspend fun getDiseaseDetail(name: String) =
        wrapWithFlow(remoteDataSource.getDiseaseDetail(name))

    suspend fun getPredict(name: String) = wrapWithFlow(remoteDataSource.getPredict(name))


    private fun <T> wrapWithFlow(function: Response<T>): Flow<Resource<T?>> {
        return flow {
            emit(Resource.Loading())
            try {
                if (function.isSuccessful) {
                    emit(Resource.Success(function.body()))
                } else {
                    emit(Resource.Error(throwable = Throwable(function.message())))
                }
            } catch (e: Exception) {
                emit(Resource.Error(throwable = e))
            }
        }
    }
}