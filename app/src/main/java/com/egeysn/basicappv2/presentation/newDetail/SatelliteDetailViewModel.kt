package com.egeysn.basicappv2.presentation.newDetail

import androidx.lifecycle.viewModelScope
import com.egeysn.basicappv2.common.utils.Resource
import com.egeysn.basicappv2.common.utils.UiText
import com.egeysn.basicappv2.data.remote.models.satellitedetail.SatelliteDetailDto
import com.egeysn.basicappv2.domain.use_cases.detail.GetSatelliteDetailUseCase
import com.egeysn.basicappv2.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class SatelliteDetailViewModel @Inject constructor(
    private val getNewDetailUseCase: GetSatelliteDetailUseCase,
) :
    BaseViewModel() {

    private val _newDetailState = MutableStateFlow<NewDetailViewState>(NewDetailViewState.Init)
    val newDetailState: StateFlow<NewDetailViewState> = _newDetailState.asStateFlow()

    private fun setLoading(isLoading: Boolean) {
        _newDetailState.value = NewDetailViewState.Loading(isLoading)
    }

    fun getNewDetail(id: Int) {
        viewModelScope.launch {
            getNewDetailUseCase.getSatelliteDetail(id).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        setLoading(false)
                        _newDetailState.value = NewDetailViewState.Error(result.message)
                    }

                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        result.data?.let {
                            _newDetailState.value = NewDetailViewState.Success(it)
                        } ?: run {
                            _newDetailState.value = NewDetailViewState.Error(result.message)
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class NewDetailViewState {
        object Init : NewDetailViewState()
        data class Loading(val isLoading: Boolean) : NewDetailViewState()
        data class Success(val data: SatelliteDetailDto) : NewDetailViewState()
        data class Error(val error: UiText) : NewDetailViewState()
    }
}
