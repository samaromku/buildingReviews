package ru.andrey.savchenko.buildingreviews.storage

import ru.andrey.savchenko.buildingreviews.network.NetworkHandler

/**
 * Created by savchenko on 15.04.18.
 */
class Utils {
    companion object {
        fun getImageFullUrl(url:String):String{
            return NetworkHandler.BASE_HOST_INNER + "/images/" + url
        }

        fun getImageBuildingUrl(url:String):String{
            return NetworkHandler.BASE_HOST_INNER + "/images/buildings/" + url
        }
    }
}