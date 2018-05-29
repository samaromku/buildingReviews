package ru.andrey.savchenko.buildingreviews.fragments.info

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.andrey.savchenko.buildingreviews.activities.onecompany.INFO
import ru.andrey.savchenko.buildingreviews.activities.onecompany.OneCompanyPresenter
import ru.andrey.savchenko.buildingreviews.base.BasePresenterProgress
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler

/**
 * Created by savchenko on 15.04.18.
 */
class InfoPresenter : ViewModel(), BasePresenterProgress {
    override var routerPresenter: OneCompanyPresenter? = null

    val company = MutableLiveData<Company>()

    fun getInfoCompany(companyId: Int) {
        corMethod<Company>(
                request = { NetworkHandler.getService().getCompany(companyId).execute() },
                onResult = {
                    company.postValue(it)
                }, fragmentKey = INFO
        )
    }
}