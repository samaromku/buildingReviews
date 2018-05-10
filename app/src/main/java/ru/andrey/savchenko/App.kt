package ru.andrey.savchenko

import android.app.Application
import android.arch.persistence.room.Room
import io.realm.Realm
import ru.andrey.savchenko.buildingreviews.db.room.RegionDataBase
import ru.andrey.savchenko.buildingreviews.storage.objectOf

/**
 * Created by savchenko on 22.04.18.
 */
class App :Application(){
    companion object {
        lateinit var database: RegionDataBase
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        database = Room.databaseBuilder(this, objectOf<RegionDataBase>(), "regionRoom.db").build()
//        Fabric.with(this, Crashlytics())
    }
}