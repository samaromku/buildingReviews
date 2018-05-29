package ru.andrey.savchenko.buildingreviews.fragments.info

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.andrey.savchenko.App
import ru.andrey.savchenko.buildingreviews.activities.moderate.ModeratePresenter
import ru.andrey.savchenko.buildingreviews.activities.onecompany.OneCompanyPresenter
import ru.andrey.savchenko.buildingreviews.activities.onecompany.PROGRESS
import ru.andrey.savchenko.buildingreviews.base.BasePresenterNoMvp
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler

/**
 * Created by savchenko on 15.04.18.
 */
class InfoPresenter : ViewModel(), BasePresenterNoMvp {
    val company = MutableLiveData<Company>()
    lateinit var routerPresenter: OneCompanyPresenter

    override fun showDialog() {
        routerPresenter.state.value = OneCompanyPresenter.ViewState.Loading
    }

    override fun hideDialog() {
        routerPresenter.state.value = OneCompanyPresenter.ViewState.Loaded
    }

    override fun showError(error: ErrorResponse, repeat: () -> Unit) {
        routerPresenter.state.value = OneCompanyPresenter.ViewState.ErrorShown
    }

    fun getInfoCompany(companyId: Int) {
        corMethod<Company>(
                request = { NetworkHandler.getService().getCompany(companyId).execute() },
                onResult = {
                    company.postValue(it)
                }
        )
    }
}