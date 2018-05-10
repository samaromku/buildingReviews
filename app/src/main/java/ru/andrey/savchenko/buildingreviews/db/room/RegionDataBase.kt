package ru.andrey.savchenko.buildingreviews.db.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by Andrey on 10.05.2018.
 */
@Database(entities = [(RegionRoom::class)], version = 2)
abstract class RegionDataBase :RoomDatabase(){
    abstract fun weatherDataDao(): RegionDao

//    companion object {
//        private var INSTANCE: RegionDataBase? = null
//
//        fun getInstance(context: Context): RegionDataBase? {
//            if (INSTANCE == null) {
//                synchronized(RegionDataBase::class) {
//                    INSTANCE = Room.databaseBuilder(context.applicationContext,
//                            RegionDataBase::class.java, "regionRoom.db")
//                            .build()
//                }
//            }
//            return INSTANCE
//        }
//
//        fun destroyInstance() {
//            INSTANCE = null
//        }
//    }
}