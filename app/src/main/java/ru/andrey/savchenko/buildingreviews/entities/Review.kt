package ru.andrey.savchenko.buildingreviews.entities

/**
 * Created by savchenko on 10.04.18.
 */
data class Review(
        val id: Int,
        val companyId:Int,
        val positive:String? = "",
        val negative:String? = "",
        val general:String? = "",
        val rating:Int,
        val peopleLike:String,
        val creatorId:Int?,
        val created:String,
        val userName:String?,
        val state:String = "",
        val like:Like? = null
)