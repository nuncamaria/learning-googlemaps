package com.nuncamaria.streethero.ui.map

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.nuncamaria.streethero.domain.GetUserLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getLocationUseCase: GetUserLocationUseCase
) : ViewModel() {

    val userLocation = mutableStateOf(LatLng(40.463667, -3.74922))

    private val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(ViewState.Idle)
    val viewState = _viewState.asStateFlow()

    fun getLocation(event: PermissionEvent) {
        _viewState.value = ViewState.Loading

        when (event) {
            PermissionEvent.Granted -> {
                viewModelScope.launch {
                    getLocationUseCase().collect { location ->
                        _viewState.value = ViewState.Success(location)
                    }
                }
            }

            PermissionEvent.Revoked -> {
                _viewState.value = ViewState.RevokedPermissions
            }
        }
    }
}


sealed interface ViewState {
    data object Idle : ViewState
    data object Loading : ViewState
    data class Success(val location: LatLng?) : ViewState
    data object RevokedPermissions : ViewState
}

sealed interface PermissionEvent {
    data object Granted : PermissionEvent
    data object Revoked : PermissionEvent
}
