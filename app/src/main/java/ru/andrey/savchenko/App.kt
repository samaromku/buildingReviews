package ru.andrey.savchenko

import android.app.Application
import ru.andrey.savchenko.buildingreviews.db.RegionDataBase

/**
 * Created by savchenko on 22.04.18.
 */
class App :Application(){
    companion object {
        lateinit var database: RegionDataBase
    }

    override fun onCreate() {
        super.onCreate()
        database = RegionDataBase.init(this)
//        Fabric.with(this, Crashlytics())
    }
}