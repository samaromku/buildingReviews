package ru.andrey.savchenko.buildingreviews.fragments.review

import com.arellomobile.mvp.InjectViewState
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler

/**
 * Created by Andrey on 13.04.2018.
 */
@InjectViewState
class ReviewPresenter : BasePresenter<ReviewView>() {
    var list: MutableList<Review>? = null

    fun getReviews(companyId: Int) {
        Coroutiner<List<Review>>(viewState).corMethod(
                request = { NetworkHandler.getService().getReviewsByCompanyId(companyId).execute() },
                onResult = { it ->
                    it.toMutableList().let {
                        if (it.isEmpty()) {
                            viewState.setNoReviewsVisible()
                            return@let
                        }
                        it.sortByDescending { it.created }
                        viewState.setListToAdapter(it)
                        list = it
                    }
                }
        )
    }
}