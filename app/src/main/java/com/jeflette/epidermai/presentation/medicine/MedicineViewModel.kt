package com.jeflette.epidermai.presentation.medicine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeflette.epidermai.data.EpidermAIRepository
import com.jeflette.epidermai.data.remote.responses.MedicinesResponse
import com.jeflette.epidermai.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(
    private val repository: EpidermAIRepository
) : ViewModel() {

    private var _medicineResult: MutableStateFlow<Resource<MedicinesResponse?>> =
        MutableStateFlow(Resource.Loading())

    val diseaseResult = _medicineResult.asStateFlow()

    fun getAllDiseases() {
        viewModelScope.launch {
            repository.getAllMedicines().collectLatest {
                _medicineResult.value = it
            }
        }
    }
}