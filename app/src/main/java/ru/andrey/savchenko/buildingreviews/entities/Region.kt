package ru.andrey.savchenko.buildingreviews.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by savchenko on 06.05.18.
 */
@Entity(tableName = "region")
data class Region(
        @PrimaryKey
        @ColumnInfo(name = "value")
        var value: String,
        @ColumnInfo(name = "selected")
        var selected: Boolean = false
)