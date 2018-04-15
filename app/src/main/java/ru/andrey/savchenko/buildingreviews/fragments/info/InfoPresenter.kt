package ru.andrey.savchenko.buildingreviews.fragments.info

import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Response
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler

/**
 * Created by savchenko on 15.04.18.
 */
@InjectViewState
class InfoPresenter:BasePresenter<InfoView>() {
    fun getInfoCompany(companyId:Int){
        launch(UI){
            viewState.showDialog()
            var result: Response<Company>? = null
            try {
                result = async(CommonPool) {
                    NetworkHandler.getService().getCompany(companyId).execute()
                }.await()
            } catch (ex: Exception) {
                ex.printStackTrace()
                viewState.showError(ex.message.toString())
            }
            result?.body()?.let {
                viewState.setToolbarText(it.title)
                viewState.setSite(it.siteUrl)
                viewState.setDescription(it.description)
                viewState.setPhone(it.phone)
                viewState.setAddress(it.address)
                viewState.setLogo(it.imageUrl)
            }
            viewState.hideDialog()
        }
    }
}