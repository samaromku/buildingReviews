package ru.andrey.savchenko.buildingreviews.fragments.review

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.andrey.savchenko.buildingreviews.activities.onecompany.INFO
import ru.andrey.savchenko.buildingreviews.activities.onecompany.OneCompanyPresenter
import ru.andrey.savchenko.buildingreviews.activities.onecompany.REVIEWS
import ru.andrey.savchenko.buildingreviews.base.BasePresenterNoMvp
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorRepeat
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler

/**
 * Created by Andrey on 13.04.2018.
 */
class ReviewPresenter : ViewModel(), BasePresenterNoMvp {
    lateinit var routerPresenter: OneCompanyPresenter

    override fun showDialog() {
        routerPresenter.showDialog()
    }

    override fun hideDialog() {
        routerPresenter.hideDialog(REVIEWS)
    }

    override fun showError(error: ErrorResponse, repeat: () -> Unit) {
        routerPresenter.showError(ErrorRepeat(error, repeat))
    }

    val list = MutableLiveData<MutableList<Review>>()

    fun getReviews(companyId: Int) {
        corMethod<List<Review>>(
                request = { NetworkHandler.getService().getReviewsByCompanyId(companyId).execute() },
                onResult = {
                    it.toMutableList().let {
                        list.value = it
                    }
                }
        )
    }
}