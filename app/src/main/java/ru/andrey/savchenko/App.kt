package ru.andrey.savchenko

import android.app.Application
import android.content.Intent
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.Module
import ru.andrey.savchenko.buildingreviews.db.RegionDataBase
import org.koin.dsl.module.applicationContext
import ru.andrey.savchenko.buildingreviews.activities.moderate.ModeratePresenter
import ru.andrey.savchenko.buildingreviews.network.Network
import org.koin.android.architecture.ext.viewModel
import ru.andrey.savchenko.buildingreviews.activities.auth.AuthActivity

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
        startKoin(this, listOf(myModule))
    }

    private val myModule : Module = applicationContext {
        bean{ Network() }
        viewModel{ModeratePresenter(get())}
    }
}