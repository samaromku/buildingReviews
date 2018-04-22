package ru.andrey.savchenko

import android.app.Application
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric

/**
 * Created by savchenko on 22.04.18.
 */
class App :Application(){
    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
    }
}