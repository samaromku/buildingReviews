package ru.andrey.savchenko.buildingreviews.services

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder

/**
 * Created by savchenko on 23.05.18.
 */
class TestService :Service() {

    override fun startService(service: Intent?): ComponentName {
        println("test simple service")
        return super.startService(service)
    }
    override fun onBind(intent: Intent?): IBinder? {

        return null
    }
}