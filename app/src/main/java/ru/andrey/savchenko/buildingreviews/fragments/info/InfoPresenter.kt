package ru.andrey.savchenko.buildingreviews.fragments.info

import com.arellomobile.mvp.InjectViewState
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler

/**
 * Created by savchenko on 15.04.18.
 */
@InjectViewState
class InfoPresenter:BasePresenter<InfoView>() {
    fun getInfoCompany(companyId:Int){
        Coroutiner<Company>(viewState).corMethod(
                request = { NetworkHandler.getService().getCompany(companyId).execute()},
                onResult = {
                    viewState.setToolbarText(it.title)
                    viewState.setSite(it.siteUrl)
                    viewState.setDescription(it.description)
                    viewState.setPhone(it.phone)
                    viewState.setAddress(it.address)
                    viewState.setLogo(it.imageUrl)
                }
        )
    }
}