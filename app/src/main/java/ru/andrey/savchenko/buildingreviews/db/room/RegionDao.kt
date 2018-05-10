package ru.andrey.savchenko.buildingreviews.db.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

/**
 * Created by Andrey on 10.05.2018.
 */
@Dao
interface RegionDao {
    @Query("SELECT * from regionRoom")
    fun getAll(): List<RegionRoom>

    @Insert(onConflict = REPLACE)
    fun insert(regionRoom: RegionRoom)
}