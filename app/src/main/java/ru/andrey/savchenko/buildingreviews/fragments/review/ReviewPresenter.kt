package ru.andrey.savchenko.buildingreviews.fragments.review

import com.arellomobile.mvp.InjectViewState
import ru.andrey.savchenko.App
import ru.andrey.savchenko.buildingreviews.activities.onecompany.ADD_REVIEW
import ru.andrey.savchenko.buildingreviews.activities.onecompany.ERROR
import ru.andrey.savchenko.buildingreviews.activities.onecompany.PROGRESS
import ru.andrey.savchenko.buildingreviews.activities.onecompany.REVIEWS
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorRepeat
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.storage.Storage

/**
 * Created by Andrey on 13.04.2018.
 */
@InjectViewState
class ReviewPresenter : BasePresenter<ReviewView>() {
    var list: MutableList<Review>? = null

    fun getReviews(companyId: Int) {
        corMethod<List<Review>>(
                request = { NetworkHandler.getService().getReviewsByCompanyId(companyId).execute() },
                onResult = {
                    it.toMutableList().let {
                        if (checkListEmpty(it)) {
                            viewState.setNoReviewsVisible()
                        } else {
                            it.sortByDescending { it.created }
                            viewState.setListToAdapter(it)
                            list = it
                        }
                    }
                }
        )
    }

    private fun checkListEmpty(list: List<Review>): Boolean = list.isEmpty()
}