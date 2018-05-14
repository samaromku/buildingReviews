package ru.andrey.savchenko.buildingreviews.db

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import ru.andrey.savchenko.App
import ru.andrey.savchenko.buildingreviews.entities.Region

/**
 * Created by savchenko on 14.05.18.
 */
class Repository {
    private val dataBase = App.database

    fun addRegion(region: Region){
        launch(UI) {
            async(CommonPool) {
                dataBase.regionDao().insertOrUpdate(region)
            }.await()
        }
    }

    fun addRegions(region: List<Region>){
        launch(UI) {
            async(CommonPool) {
                dataBase.regionDao().insertOrUpdateAll(region)
            }.await()
        }
    }

    suspend fun getAllRegions():MutableList<Region>{
        return async(CommonPool) {
            dataBase.regionDao().getAll().toMutableList()
        }.await()
    }
}