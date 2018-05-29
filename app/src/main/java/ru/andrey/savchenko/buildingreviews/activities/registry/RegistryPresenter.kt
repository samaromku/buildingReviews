package ru.andrey.savchenko.buildingreviews.activities.registry

import com.arellomobile.mvp.InjectViewState
import ru.andrey.savchenko.App
import ru.andrey.savchenko.buildingreviews.base.BaseMoxyPresenter
import ru.andrey.savchenko.buildingreviews.entities.User
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.storage.Storage

/**
 * Created by savchenko on 16.04.18.
 */
@InjectViewState
class RegistryPresenter : BaseMoxyPresenter<RegistryView>() {
    fun registerUser(login: String,
                     password: String,
                     name: String,
                     email: String) {
        if (checkFieldsValidation(login, password, name, email)) {
            viewState.showToast("Заполните все поля")
        } else {
            corMethod(
                    request = {
                        NetworkHandler.getService().register(User(
                                id = 0,
                                login = login,
                                password = password,
                                name = name,
                                email = email)).execute()
                    }, onResult = {
                Storage.user = it
                App.cicerone.router.newRootScreen(Storage.keyGoBackAfterAuth)
//                App.cicerone.router.backTo(Storage.keyGoBackAfterAuth)
            }
            )
        }
    }

    private fun checkFieldsValidation(login: String,
                                      password: String,
                                      name: String,
                                      email: String): Boolean {
        return login.isEmpty() ||
                password.isEmpty() ||
                email.isEmpty() ||
                name.isEmpty()
    }
}