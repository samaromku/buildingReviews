package ru.andrey.savchenko.buildingreviews.services

import android.app.IntentService
import android.content.Intent

/**
 * Created by savchenko on 23.05.18.
 */
class TestIntentService :IntentService("testIntentService") {
    override fun onHandleIntent(intent: Intent?) {
        println("test intent service")
    }
}