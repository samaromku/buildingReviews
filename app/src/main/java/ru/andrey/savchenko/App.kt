package ru.andrey.savchenko

import android.app.Application
import ru.andrey.savchenko.buildingreviews.db.RegionDataBase
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

/**
 * Created by savchenko on 22.04.18.
 */
class App : Application() {
    companion object {
        lateinit var database: RegionDataBase
        lateinit var cicerone: Cicerone<Router>
    }


    override fun onCreate() {
        super.onCreate()
        database = RegionDataBase.init(this)
        cicerone = Cicerone.create()
//        Fabric.with(this, Crashlytics())
    }
}