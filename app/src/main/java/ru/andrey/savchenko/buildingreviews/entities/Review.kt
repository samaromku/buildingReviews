package ru.andrey.savchenko.buildingreviews.entities

/**
 * Created by savchenko on 10.04.18.
 */
data class Review(
        val id: Int,
        val description: String,
        val rating:Int,
        val peopleLike:String,
        val creatorId:Int,
        val created:String
)