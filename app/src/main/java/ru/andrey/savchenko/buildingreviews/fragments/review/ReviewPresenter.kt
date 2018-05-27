package ru.andrey.savchenko.buildingreviews.fragments.review

import ru.andrey.savchenko.buildingreviews.base.BasePresenterNoMvp
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler

/**
 * Created by Andrey on 13.04.2018.
 */
class ReviewPresenter(var view: ReviewView?) : BasePresenterNoMvp {

    fun onAttach(view: ReviewView?) {
        this.view = view
    }

    fun onDetach() {
        this.view = null
    }

    override fun showDialog() {
        view?.showDialog()
    }

    override fun hideDialog() {
        view?.hideDialog()
    }

    override fun showError(error: ErrorResponse, repeat: () -> Unit) {
        view?.showError(error, repeat)
    }

    var list: MutableList<Review>? = null

    fun getReviews(companyId: Int) {
        corMethod<List<Review>>(
                request = { NetworkHandler.getService().getReviewsByCompanyId(companyId).execute() },
                onResult = {
                    it.toMutableList().let {
                        if (checkListEmpty(it)) {
                            view?.setNoReviewsVisible()
                        } else {
                            it.sortByDescending { it.created }
                            view?.setListToAdapter(it)
                            list = it
                        }
                    }
                }, beforeRequest = {},
                afterRequest = {}
        )
    }

    private fun checkListEmpty(list: List<Review>): Boolean = list.isEmpty()
}