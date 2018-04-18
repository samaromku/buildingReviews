package ru.andrey.savchenko.buildingreviews.fragments.buildings

import com.arellomobile.mvp.InjectViewState
import entities.Building
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.storage.Coroutiner

/**
 * Created by savchenko on 15.04.18.
 */
@InjectViewState
class BuildingPresenter: BasePresenter<BuildingView>() {
    var list: MutableList<Building>? = null

    fun getBuildingsByCompanyId(companyId:Int){
        Coroutiner<List<Building>>(viewState).corMethod(
                request = {NetworkHandler.getService().getBuildingsByCompanyId(companyId).execute()},
                onResult = {it ->
                    it.toMutableList().let {
                        viewState.setListToAdapter(it)
                        list = it
                    }
                }
        )
    }
}