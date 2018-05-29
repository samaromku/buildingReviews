package ru.andrey.savchenko.buildingreviews.activities.auth

import com.arellomobile.mvp.InjectViewState
import ru.andrey.savchenko.App
import ru.andrey.savchenko.buildingreviews.activities.onecompany.REGISTER
import ru.andrey.savchenko.buildingreviews.base.BaseMoxyPresenter
import ru.andrey.savchenko.buildingreviews.entities.User
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.storage.Const.Companion.FILL_FIELDS
import ru.andrey.savchenko.buildingreviews.storage.Storage

/**
 * Created by savchenko on 16.04.18.
 */
@InjectViewState
class AuthPresenter : BaseMoxyPresenter<AuthView>() {
    fun auth(login: String,
             password: String) {
        if (login.isEmpty() || password.isEmpty()) {
            viewState.showToast(FILL_FIELDS)
            return
        } else {
            corMethod<User>(
                    request = { NetworkHandler.getService().auth(login, password).execute() },
                    onResult = {
                        Storage.user = it
                        App.cicerone.router.newRootScreen(Storage.keyGoBackAfterAuth)
//                        App.cicerone.router.replaceScreen(Storage.keyGoBackAfterAuth)
                    }, afterRequest = {},
                    beforeRequest = {}
            )
        }
    }

    fun startRegister(){
        App.cicerone.router.replaceScreen(REGISTER)
    }

    fun checkAuthStart() {
        if (Storage.user?.token != null) {
            viewState.startCompaniesActivity()
        }
    }


}