package ru.andrey.savchenko.buildingreviews.db.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Andrey on 10.05.2018.
 */
@Entity(tableName = "regionRoom")
data class RegionRoom (
        @PrimaryKey
        @ColumnInfo(name = "value")
        var value: String,
        @ColumnInfo(name = "selected")
        var selected: Boolean
){
}