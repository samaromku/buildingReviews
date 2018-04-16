package ru.andrey.savchenko.buildingreviews.activities.registry

import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Response
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.User
import ru.andrey.savchenko.buildingreviews.entities.network.ApiResponse
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.storage.Storage

/**
 * Created by savchenko on 16.04.18.
 */
@InjectViewState
class RegistryPresenter:BasePresenter<RegistryView>() {
    fun registerUser(login:String,
                     password:String,
                     name:String){
        launch(UI) {
            viewState.showDialog()
            var result: Response<ApiResponse<User>>? = null
            try {
                result = async(CommonPool) {
                    NetworkHandler.getService().register(User(
                            id = 0,
                            login = login,
                            password = password,
                            name = name)).execute()
                }.await()
            } catch (ex: Exception) {
                ex.printStackTrace()
                viewState.showError(ex.message.toString())
            }
            result?.body()?.let {
                checkResponse(it, {
                    Storage.user = it.data
                    viewState.startCompaniesActivity()
                })
            }
            viewState.hideDialog()
        }
    }
}