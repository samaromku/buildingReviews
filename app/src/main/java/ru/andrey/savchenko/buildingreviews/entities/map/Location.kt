package ru.andrey.savchenko.buildingreviews.entities.map

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by savchenko on 04.05.18.
 */
data class Location(
        @SerializedName("lat")
        @Expose
        val lat: Double = 0.toDouble(),
        @SerializedName("lng")
        @Expose
        val lng: Double = 0.toDouble()
)