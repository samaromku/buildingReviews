package ru.andrey.savchenko.buildingreviews.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by savchenko on 06.05.18.
 */
open class Region(
        @PrimaryKey
        var value: String = "",
        var selected: Boolean = false
) : RealmObject()