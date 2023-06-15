package com.jeflette.epidermai.presentation.medicinedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeflette.epidermai.data.EpidermAIRepository
import com.jeflette.epidermai.data.remote.responses.MedicinesDetailResponse
import com.jeflette.epidermai.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineDetailViewModel @Inject constructor(
    private val repository: EpidermAIRepository
) : ViewModel() {
    private var _medicineResult: MutableStateFlow<Resource<MedicinesDetailResponse?>> =
        MutableStateFlow(Resource.Loading())

    val medicineResult = _medicineResult.asStateFlow()

    fun getMedicineDetail(name: String) {
        viewModelScope.launch {
            repository.getMedicineDetail(name).collectLatest {
                _medicineResult.value = it
            }
        }
    }
}