package com.egeysn.basicappv2.presentation.satelliteDetail

import androidx.lifecycle.viewModelScope
import com.egeysn.basicappv2.common.utils.Resource
import com.egeysn.basicappv2.common.utils.UiText
import com.egeysn.basicappv2.data.remote.models.positions.Position
import com.egeysn.basicappv2.data.remote.models.positions.PositionInfo
import com.egeysn.basicappv2.data.remote.models.satellitedetail.SatelliteDetailDto
import com.egeysn.basicappv2.domain.use_cases.detail.GetSatelliteDetailUseCase
import com.egeysn.basicappv2.domain.use_cases.positions.GetPositionUseCase
import com.egeysn.basicappv2.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.CancellationException

@HiltViewModel
class SatelliteDetailViewModel @Inject constructor(
    private val getSatelliteDetailUseCase: GetSatelliteDetailUseCase,
    private val getPositionUseCase: GetPositionUseCase,
) :
    BaseViewModel() {

    private val _satelliteDetailState =
        MutableStateFlow<SatelliteDetailViewState>(SatelliteDetailViewState.Init)
    val satelliteDetailState: StateFlow<SatelliteDetailViewState> = _satelliteDetailState.asStateFlow()

    private val _satellitePositionState = MutableStateFlow<Position?>(null)
    val satellitePositionState: StateFlow<Position?> = _satellitePositionState.asStateFlow()

    private var positionIndex: Int = 0
    private var isActive = true

    private fun setLoading(isLoading: Boolean) {
        _satelliteDetailState.value = SatelliteDetailViewState.Loading(isLoading)
    }

    fun getSatelliteDetail(id: Int) {
        viewModelScope.launch {
            getSatelliteDetailUseCase.getSatelliteDetail(id).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        setLoading(false)
                        _satelliteDetailState.value = SatelliteDetailViewState.Error(result.message)
                    }

                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        result.data?.let {
                            _satelliteDetailState.value = SatelliteDetailViewState.Success(it)
                        } ?: run {
                            _satelliteDetailState.value =
                                SatelliteDetailViewState.Error(result.message)
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    fun fetchPositions(id: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            getPositionUseCase.getPosition(id).onEach { result ->
                result.data?.let {
                    schedulePositions(it, it.positions.size)
                }
            }.launchIn(this)
        }
    }

    fun disableSchedulePositions(){
        isActive = false
    }
    private fun schedulePositions(info: PositionInfo, size: Int) {
         viewModelScope.launch(Dispatchers.Main) {
            while (isActive) {
                try {
                    if (positionIndex >= size) positionIndex = 0
                    _satellitePositionState.value = info.positions[positionIndex++]
                    delay(3000)
                } catch (e: Exception) {
                    Timber.e(e)
                }
            }
        }
    }

    sealed class SatelliteDetailViewState {
        object Init : SatelliteDetailViewState()
        data class Loading(val isLoading: Boolean) : SatelliteDetailViewState()
        data class Success(val data: SatelliteDetailDto) : SatelliteDetailViewState()
        data class Error(val error: UiText) : SatelliteDetailViewState()
    }
}
