package ru.andrey.savchenko.buildingreviews.storage

import ru.andrey.savchenko.buildingreviews.entities.ConstDict
import ru.andrey.savchenko.buildingreviews.entities.User

/**
 * Created by savchenko on 16.04.18.
 */
object Storage {
    var user:User? = null
    var constDictList = mutableListOf<ConstDict>()

    fun getValueConst(name:String):String?{
        return constDictList.firstOrNull { it.name==name }?.value
    }
}