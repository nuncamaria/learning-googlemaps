package com.nuncamaria.streethero.domain.model

import com.google.android.gms.maps.model.LatLng

data class PlaceModel(
    var placeId: String = "",
    var postalCode: String? = "",
    var locality: String = "",
    var country: String = "",
    val latLng: LatLng,
    val address: LatLng? = null
)