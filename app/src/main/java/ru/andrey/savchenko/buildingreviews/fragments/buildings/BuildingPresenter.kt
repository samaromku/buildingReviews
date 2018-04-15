package ru.andrey.savchenko.buildingreviews.fragments.buildings

import com.arellomobile.mvp.InjectViewState
import entities.Building
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Response
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler

/**
 * Created by savchenko on 15.04.18.
 */
@InjectViewState
class BuildingPresenter: BasePresenter<BuildingView>() {
    var list: MutableList<Building>? = null

    fun getBuildingsByCompanyId(companyId:Int){
        launch(UI) {
            viewState.showDialog()
            var result: Response<List<Building>>? = null
            try {
                result = async(CommonPool) {
                    NetworkHandler.getService().getBuildingsByCompanyId(companyId).execute()
                }.await()
            } catch (ex: Exception) {
                ex.printStackTrace()
                viewState.showError(ex.message.toString())
            }
            result?.body()?.toMutableList()?.let {
                viewState.setListToAdapter(it)
                list = it
            }
            viewState.hideDialog()
        }
    }
}