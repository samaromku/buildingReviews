package ru.andrey.savchenko.buildingreviews.fragments.buildings

import com.arellomobile.mvp.InjectViewState
import entities.Building
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.network.ApiResponse
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler

/**
 * Created by savchenko on 15.04.18.
 */
@InjectViewState
class BuildingPresenter: BasePresenter<BuildingView>() {
    var list: MutableList<Building>? = null

    fun getBuildingsByCompanyId(companyId:Int){
        corMethod<List<Building>>(
                request = {NetworkHandler.getService().getBuildingsByCompanyId(companyId).execute()},
                onResult = {it ->
                    it.toMutableList().let {
                        if(it.isNotEmpty()) {
                            viewState.setListToAdapter(it)
                            list = it
                        }else {
                            viewState.setTextEmptyBuildings("К сожалению, у нас нет данных о проектах данного застройщика")
                        }
                    }
                }
        )
    }
}