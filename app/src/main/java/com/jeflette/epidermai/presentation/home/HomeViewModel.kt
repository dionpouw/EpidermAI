package com.jeflette.epidermai.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeflette.epidermai.data.EpidermAIRepository
import com.jeflette.epidermai.data.local.model.CommonItem
import com.jeflette.epidermai.data.remote.responses.DiseasesResponse
import com.jeflette.epidermai.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import provideBlog
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: EpidermAIRepository
) : ViewModel(
) {
    private var _blogResult: MutableStateFlow<Resource<List<CommonItem>>> =
        MutableStateFlow(Resource.Loading())

    val blogResult = _blogResult.asStateFlow()

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

    fun getBlog() {
        viewModelScope.launch {
            _blogResult.value = Resource.Success(provideBlog())
        }
    }

}