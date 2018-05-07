package ru.andrey.savchenko

import android.app.Application
import io.realm.Realm

/**
 * Created by savchenko on 22.04.18.
 */
class App :Application(){
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
//        Fabric.with(this, Crashlytics())
    }
}