package ru.andrey.savchenko.buildingreviews.entities.map

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by savchenko on 04.05.18.
 */
data class GeoCode(
        @SerializedName("results")
        @Expose
        val results: List<Result>? = null,
        @SerializedName("status")
        @Expose
        val status: String? = null
)