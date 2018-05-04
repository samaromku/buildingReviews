package ru.andrey.savchenko.buildingreviews.entities.map

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by savchenko on 04.05.18.
 */
data class Viewport(
        @SerializedName("northeast")
        @Expose
        val northeast: Northeast? = null,
        @SerializedName("southwest")
        @Expose
        val southwest: Southwest? = null
)