package com.nuncamaria.streethero

import com.google.android.libraries.places.api.net.PlacesClient

object PlacesManager{

    lateinit var placesClient: PlacesClient

    // Construct a PlacesClient
 //   placesClient.initialize(applicationContext, getString(R.string.maps_api_key))
 //   placesClient = Places.createClient(this)

    // Construct a FusedLocationProviderClient.
//    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

}