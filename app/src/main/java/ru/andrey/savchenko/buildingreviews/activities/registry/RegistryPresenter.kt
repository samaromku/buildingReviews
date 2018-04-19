package ru.andrey.savchenko.buildingreviews.activities.registry

import com.arellomobile.mvp.InjectViewState
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.User
import ru.andrey.savchenko.buildingreviews.entities.network.ApiResponse
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.storage.Storage

/**
 * Created by savchenko on 16.04.18.
 */
@InjectViewState
class RegistryPresenter : BasePresenter<RegistryView>() {
    fun registerUser(login: String,
                     password: String,
                     name: String,
                     email: String) {
        if (login.isEmpty() ||
                password.isEmpty() ||
                email.isEmpty() ||
                name.isEmpty()) {
            viewState.showToast("Заполните все поля")
            return
        }
        corMethod<User>(
                request = {
                    NetworkHandler.getService().register(User(
                            id = 0,
                            login = login,
                            password = password,
                            name = name,
                            email = email)).execute()
                }, onResult = {
            Storage.user = it
            viewState.startCompaniesActivity()
        }
        )
    }
}