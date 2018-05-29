package ru.andrey.savchenko.buildingreviews.fragments.review

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.andrey.savchenko.buildingreviews.activities.onecompany.OneCompanyPresenter
import ru.andrey.savchenko.buildingreviews.activities.onecompany.REVIEWS
import ru.andrey.savchenko.buildingreviews.base.BasePresenterProgress
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.storage.Storage

/**
 * Created by Andrey on 13.04.2018.
 */
class ReviewPresenter : ViewModel(), BasePresenterProgress {
    override var routerPresenter: OneCompanyPresenter? = null

    val list = MutableLiveData<MutableList<Review>>()

    fun getReviews(companyId: Int) {
        corMethod<List<Review>>(
                request = { NetworkHandler.getService().getReviewsByCompanyId(companyId).execute() },
                onResult = {
                    it.toMutableList().let {
                        list.value = it
                    }
                }, fragmentKey = REVIEWS
        )
    }

    override fun showError(error: ErrorResponse, repeat: () -> Unit) {
        Storage.keyGoBackAfterAuth = REVIEWS
        super.showError(error, repeat)
    }
}