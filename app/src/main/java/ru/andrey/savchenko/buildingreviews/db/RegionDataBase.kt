package ru.andrey.savchenko.buildingreviews.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import ru.andrey.savchenko.buildingreviews.entities.Region
import ru.andrey.savchenko.buildingreviews.storage.objectOf

/**
 * Created by Andrey on 10.05.2018.
 */
@Database(entities = [(Region::class)], version =4)
abstract class RegionDataBase :RoomDatabase(){
    abstract fun regionDao(): RegionDao

    companion object {
        fun init(context: Context): RegionDataBase {
            return Room.databaseBuilder(context, objectOf<RegionDataBase>(),"region.db").build()
        }
    }
}