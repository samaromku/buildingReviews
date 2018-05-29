package ru.andrey.savchenko.buildingreviews.fragments.buildings

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.arellomobile.mvp.InjectViewState
import entities.Building
import ru.andrey.savchenko.buildingreviews.activities.onecompany.BUILDINGS
import ru.andrey.savchenko.buildingreviews.activities.onecompany.OneCompanyPresenter
import ru.andrey.savchenko.buildingreviews.base.BaseMoxyPresenter
import ru.andrey.savchenko.buildingreviews.base.BasePresenterProgress
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.storage.Const.Companion.NO_DATA_ABOUT_PROJECTS

/**
 * Created by savchenko on 15.04.18.
 */
class BuildingPresenter: ViewModel(), BasePresenterProgress {
    override var routerPresenter: OneCompanyPresenter?=null
    val list = MutableLiveData<MutableList<Building>>()

    fun getBuildingsByCompanyId(companyId:Int){
        corMethod<List<Building>>(
                request = {NetworkHandler.getService().getBuildingsByCompanyId(companyId).execute()},
                onResult = {it ->
                    it.toMutableList().let {
                        list.value = it
                    }
                }, fragmentKey = BUILDINGS
        )
    }
}