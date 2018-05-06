package ru.andrey.savchenko.buildingreviews.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.andrey.savchenko.buildingreviews.entities.map.GeoCode

/**
 * Created by savchenko on 04.05.18.
 */
interface GeoCodeService {
    @GET("/maps/api/geocode/json")
    fun geoCode(@Query("latlng") latLng: String,
                @Query("key") key: String,
                @Query("language")language:String):Call<GeoCode>
}