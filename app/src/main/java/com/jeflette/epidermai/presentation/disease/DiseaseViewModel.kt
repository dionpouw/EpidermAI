package com.jeflette.epidermai.presentation.disease

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeflette.epidermai.data.EpidermAIRepository
import com.jeflette.epidermai.data.remote.responses.DiseasesResponse
import com.jeflette.epidermai.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiseaseViewModel @Inject constructor(
    private val repository: EpidermAIRepository
) : ViewModel() {

    private var _diseaseResult: MutableStateFlow<Resource<DiseasesResponse?>> =
        MutableStateFlow(Resource.Loading())

    val diseaseResult = _diseaseResult.asStateFlow()

    fun getAllDiseases() {
        viewModelScope.launch {
            repository.getAllDiseases().collectLatest {
                _diseaseResult.value = it
            }
        }
    }
}