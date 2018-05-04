package ru.andrey.savchenko.buildingreviews.entities.map

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by savchenko on 04.05.18.
 */
class AddressComponent(
        @SerializedName("long_name")
        @Expose
        val longName: String? = null,
        @SerializedName("short_name")
        @Expose
        val shortName: String? = null,
        @SerializedName("types")
        @Expose
        val types: List<String>? = null
)