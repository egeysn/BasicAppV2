package com.egeysn.basicappv2.presentation.locations

import androidx.lifecycle.viewModelScope
import com.egeysn.basicappv2.R
import com.egeysn.basicappv2.common.utils.Resource
import com.egeysn.basicappv2.common.utils.UiText
import com.egeysn.basicappv2.domain.models.SatelliteItem
import com.egeysn.basicappv2.domain.use_cases.search.GetLocationsUseCase
import com.egeysn.basicappv2.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val searchUserUseCase: GetLocationsUseCase
) : BaseViewModel() {
    private val _state = MutableStateFlow<SearchViewState>(SearchViewState.Init)
    fun getViewState(): StateFlow<SearchViewState> = _state.asStateFlow()

    private var searchJob: Job? = null

    private fun setLoading(isLoading: Boolean) {
        _state.value = SearchViewState.Loading(isLoading)
    }

    fun searchUser(query: String = "") {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            searchUserUseCase.getLocations(query).onEach {
                when (it) {
                    is Resource.Error -> {
                        setLoading(false)
                        _state.value = SearchViewState.Error(it.message)
                    }
                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        if (!it.data.isNullOrEmpty()) {
                            _state.value = SearchViewState.Success(it.data)
                        } else {
                            _state.value = SearchViewState.Error(
                                UiText.StringResource(R.string.search_location_empty_error),
                            )
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class SearchViewState {
        object Init : SearchViewState()
        data class Loading(val isLoading: Boolean) : SearchViewState()
        data class Success(val data: List<SatelliteItem>) : SearchViewState()
        data class Error(val error: UiText) : SearchViewState()
    }
}
