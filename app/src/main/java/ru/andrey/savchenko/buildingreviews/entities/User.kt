package ru.andrey.savchenko.buildingreviews.entities

/**
 * Created by savchenko on 10.04.18.
 */
data class User(
        val id:Int,
        val login:String,
        val password:String,
        val name:String,
        val userRole:String = "",
        val phone :String = "",
        val email:String = "",
        var token:Token? = null
)