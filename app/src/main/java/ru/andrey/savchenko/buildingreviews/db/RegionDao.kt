package ru.andrey.savchenko.buildingreviews.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import ru.andrey.savchenko.buildingreviews.entities.Region

/**
 * Created by Andrey on 10.05.2018.
 */
@Dao
interface RegionDao {
    @Query("SELECT * from region")
    fun getAll(): List<Region>

    @Insert(onConflict = REPLACE)
    fun insert(region: Region)

    @Insert(onConflict = REPLACE)
    fun insertAll(region: List<Region>)
}