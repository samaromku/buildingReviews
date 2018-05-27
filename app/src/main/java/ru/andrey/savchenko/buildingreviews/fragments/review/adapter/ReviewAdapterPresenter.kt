package ru.andrey.savchenko.buildingreviews.fragments.review.adapter

import ru.andrey.savchenko.App
import ru.andrey.savchenko.buildingreviews.activities.onecompany.ERROR
import ru.andrey.savchenko.buildingreviews.activities.onecompany.PROGRESS
import ru.andrey.savchenko.buildingreviews.activities.onecompany.REVIEWS
import ru.andrey.savchenko.buildingreviews.base.BasePresenterNoMvp
import ru.andrey.savchenko.buildingreviews.entities.Like
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorRepeat
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.storage.Storage

/**
 * Created by savchenko on 22.04.18.
 */
class ReviewAdapterPresenter(val view: ReviewAdapterView,
                             val list: MutableList<Review>) : BasePresenterNoMvp {


    fun sendLike(reviewId: Int, state: Int, position: Int) {
        corMethod<Review>(request = {
            NetworkHandler.getService().addLike(Like(
                    userId = 0,
                    reviewId = reviewId,
                    state = state
            )).execute()
        }, onResult = { reviewResponse ->
            list[position] = reviewResponse
            view.updateAdapter()
        }, beforeRequest = {
//            App.cicerone.router.navigateTo(PROGRESS)
        }, afterRequest = {
//            App.cicerone.router.exit()
        },
                errorShow = {
                    val errorRepeat = ErrorRepeat(it, {
                        println("repeat crash")
                        sendLike(reviewId, state, position)
                    })
                    Storage.keyGoBackAfterAuth = REVIEWS
                    App.cicerone.router.navigateTo(ERROR, errorRepeat)
                })
    }

    override fun showDialog() {
        view.showDialog()
    }

    override fun hideDialog() {
        view.hideDialog()
    }

    override fun showError(error: ErrorResponse, repeat: () -> Unit) {
        view.showError(error)
    }
}