package ru.andrey.savchenko

import android.app.Application

/**
 * Created by savchenko on 22.04.18.
 */
class App :Application(){
    override fun onCreate() {
        super.onCreate()
//        Fabric.with(this, Crashlytics())
    }
}