package ru.andrey.savchenko.buildingreviews.entities.map

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by savchenko on 04.05.18.
 */
data class Geometry(
        @SerializedName("location")
        @Expose
        val location: Location? = null,
        @SerializedName("location_type")
        @Expose
        val locationType: String? = null,
        @SerializedName("viewport")
        @Expose
        val viewport: Viewport? = null
)