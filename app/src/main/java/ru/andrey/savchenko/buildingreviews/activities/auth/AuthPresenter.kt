package ru.andrey.savchenko.buildingreviews.activities.auth

import com.arellomobile.mvp.InjectViewState
import ru.andrey.savchenko.App
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.User
import ru.andrey.savchenko.buildingreviews.entities.network.ApiResponse
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.storage.Const.Companion.FILL_FIELDS
import ru.andrey.savchenko.buildingreviews.storage.Storage

/**
 * Created by savchenko on 16.04.18.
 */
@InjectViewState
class AuthPresenter : BasePresenter<AuthView>() {
    fun auth(login: String,
             password: String,
             key:String) {
        if (login.isEmpty() || password.isEmpty()) {
            viewState.showToast(FILL_FIELDS)
            return
        } else {
            corMethod<User>(
                    request = { NetworkHandler.getService().auth(login, password).execute() },
                    onResult = {
                        Storage.user = it
                        App.cicerone.router.backTo(key)
//                        viewState.startCompaniesActivity()
                    }
            )
        }
    }

    fun checkAuthStart() {
        if (Storage.user?.token != null) {
            viewState.startCompaniesActivity()
        }
    }


}