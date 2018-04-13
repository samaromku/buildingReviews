package ru.andrey.savchenko.buildingreviews.activities.search

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.rxtransform.Validator

/**
 * Created by savchenko on 10.04.18.
 */
class SearchInterActor {
    fun getCompanyList():Single<MutableList<Company>>{
        return NetworkHandler.getService().getCompanies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { list -> return@map list.toMutableList() }
    }
}