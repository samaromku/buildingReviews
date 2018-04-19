package ru.andrey.savchenko.buildingreviews.entities

/**
 * Created by savchenko on 15.04.18.
 */
data class Like (
        val id :Int,
        val userId:Int,
        val reviewId:Int,
        var state:Int
)