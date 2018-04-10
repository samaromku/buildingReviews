package ru.andrey.savchenko.buildingreviews.entities

import java.util.*

/**
 * Created by savchenko on 10.04.18.
 */
data class Review(
        val id: Int,
        val title: String,
        val description: String,
        val rating:Int,
        val creatorId:Int,
        val created:Date
)