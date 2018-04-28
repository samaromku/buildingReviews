package ru.andrey.savchenko.buildingreviews.fragments.choose_region

import ru.andrey.savchenko.buildingreviews.base.BasePresenterNoMvp
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler

/**
 * Created by savchenko on 24.04.18.
 */
class ChooseRegionPresenter(val view:ChooseRegionView):BasePresenterNoMvp {
    var list:MutableList<String>? = null

    fun getRegions(){
        corMethod(request = {NetworkHandler.getService().getRegions().execute()},
                onResult = {
                    list = it
                    list?.let {
                        view.setListToAdapter(it)
                    }
                })
    }

    fun clickOnRegion(position:Int){
        list?.let {
            view.chooseRegion(it[position])
        }
    }

    override fun showDialog() {

    }

    override fun hideDialog() {

    }

    override fun showError(error: ErrorResponse, repeat: () -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}