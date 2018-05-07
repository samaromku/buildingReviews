package ru.andrey.savchenko.buildingreviews.db

import io.realm.Realm
import ru.andrey.savchenko.buildingreviews.entities.Region

/**
 * Created by savchenko on 07.05.18.
 */
class Dao {
    val VALUE = "value"

    fun setRegionSelected(region: Region){
        val instance = Realm.getDefaultInstance()
        val foundRegion = instance.where(Region::class.java)
                .equalTo(VALUE, region.value)
                .findFirst()
        instance.executeTransaction {
            foundRegion?.selected?.let {
                foundRegion.selected = !it
            }
        }
        instance.close()
    }

}