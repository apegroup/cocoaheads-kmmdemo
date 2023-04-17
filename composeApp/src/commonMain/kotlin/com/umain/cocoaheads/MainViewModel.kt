package com.umain.cocoaheads

import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import com.umain.cocoaheads.domain.data.BaseScreenState
import com.umain.cocoaheads.domain.datasources.GeoIpService
import com.umain.cocoaheads.domain.datasources.ReqresService
import com.umain.cocoaheads.domain.models.LocationResponse
import com.umain.cocoaheads.domain.models.RootResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

data class ScreenState(
    override var message: String? = null,
    override var loading: Boolean = false,
    var location: LocationResponse? = null,
    var users: MutableList<RootResponse.Data?>? = mutableListOf(),
) : BaseScreenState()

open class MainViewModel : KMMViewModel(), KoinComponent {

    @NativeCoroutinesState
    private val _uiState = MutableStateFlow<ScreenState>(viewModelScope, ScreenState(loading = true))

    @NativeCoroutinesState
    val uiState: StateFlow<ScreenState> = _uiState.asStateFlow()

    @NativeCoroutinesState
    private val userService: ReqresService by inject()

    @NativeCoroutinesState
    private val locationService: GeoIpService by inject()

    fun observeViewModel() {
        viewModelScope.coroutineScope.launch {
            loadUsers()
            loadLocation()
        }
    }

    @NativeCoroutines
    private suspend fun loadUsers() {
        try {
            _uiState.update {
                it.copy(loading = true)
            }

            _uiState.update {
                it.copy(
                    users = (userService.getUsers().data.orEmpty()).toMutableList(),
                    message = "Users loaded with success",
                    loading = false,
                )
            }
        } catch (ex: Exception) {
            _uiState.update {
                it.copy(
                    loading = false,
                    message = ex.message,
                )
            }
            ex.printStackTrace()
        }
    }

    @NativeCoroutines
    private suspend fun loadLocation() {
        try {
            _uiState.update {
                it.copy(loading = true)
            }

            _uiState.update {
                it.copy(
                    location = locationService.getLocation(),
                    message = "Location loaded with success",
                    loading = false,
                )
            }
        } catch (ex: Exception) {
            _uiState.update {
                it.copy(
                    loading = false,
                    message = ex.message,
                )
            }
            ex.printStackTrace()
        }
    }

    fun clear() {
        _uiState.value.users?.clear()
        _uiState.value.location = null
    }
}
