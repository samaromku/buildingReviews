package ru.andrey.savchenko.buildingreviews.fragments.review.adapter

import kotlinx.coroutines.experimental.Job
import ru.andrey.savchenko.buildingreviews.base.BasePresenterNoMvp
import ru.andrey.savchenko.buildingreviews.entities.Like
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler

/**
 * Created by savchenko on 22.04.18.
 */
class ReviewAdapterPresenter(val view: ReviewAdapterView, val list: MutableList<Review>) : BasePresenterNoMvp {

    lateinit var currentRequest :Job

    fun sendLike(reviewId: Int, state: Int, position: Int): Job {
        currentRequest = corMethod<Review>(request = {
            NetworkHandler.getService().addLike(Like(
                    userId = 0,
                    reviewId = reviewId,
                    state = state
            )).execute()
        }, onResult = { reviewResponse ->
            list[position] = reviewResponse
            view.updateAdapter()
        })
        return currentRequest
    }

    override fun showDialog() {
        view.showDialog()
    }

    override fun hideDialog() {
        view.hideDialog()
    }

    override fun showError(error: String, repeat:() -> Unit) {
        view.showError(error)
    }
}