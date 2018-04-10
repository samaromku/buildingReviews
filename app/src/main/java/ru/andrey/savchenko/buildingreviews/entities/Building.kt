package ru.andrey.savchenko.buildingreviews.entities

/**
 * Created by savchenko on 10.04.18.
 */
data class Building (
        val id:Int,
        val title:String,
        val description:String,
        val lat:Double,
        val lng:Double
)