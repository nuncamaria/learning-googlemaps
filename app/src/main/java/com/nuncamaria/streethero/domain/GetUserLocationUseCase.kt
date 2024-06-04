package com.nuncamaria.streethero.domain

import com.google.android.gms.maps.model.LatLng
import com.nuncamaria.streethero.data.LocationService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserLocationUseCase @Inject constructor(private val locationService: LocationService) {

    operator fun invoke(): Flow<LatLng?> = locationService.requestLocationUpdates()
}