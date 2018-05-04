package ru.andrey.savchenko.buildingreviews.entities.map

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


/**
 * Created by savchenko on 04.05.18.
 */
data class Result(
        @SerializedName("address_components")
        @Expose
        val addressComponents: List<AddressComponent>? = null,
        @SerializedName("formatted_address")
        @Expose
        val formattedAddress: String? = null,
        @SerializedName("geometry")
        @Expose
        val geometry: Geometry? = null,
        @SerializedName("place_id")
        @Expose
        val placeId: String? = null,
        @SerializedName("types")
        @Expose
        val types: List<String>? = null
)