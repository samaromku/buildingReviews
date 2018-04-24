package ru.andrey.savchenko.buildingreviews.entities

/**
 * Created by savchenko on 10.04.18.
 */
data class Company(
        val id: Int,
        var imageUrl: String = "",
        val phone: String = "",
        val siteUrl: String = "",
        val title: String = "",
        val description: String = "",
        val address :String= "",
        val regions:String? = ""
)