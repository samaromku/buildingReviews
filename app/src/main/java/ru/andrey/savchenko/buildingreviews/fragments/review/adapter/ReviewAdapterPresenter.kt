package ru.andrey.savchenko.buildingreviews.fragments.review.adapter

import ru.andrey.savchenko.buildingreviews.base.BasePresenterNoMvp
import ru.andrey.savchenko.buildingreviews.entities.Like
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler

/**
 * Created by savchenko on 22.04.18.
 */
class ReviewAdapterPresenter(val view:ReviewAdapterView):BasePresenterNoMvp {

    fun sendLike(reviewId:Int){
        corMethod<Like>(request = {
            NetworkHandler.getService().addLike(Like(
                    id = 0,
                    userId = 0,
                    reviewId = reviewId,
                    state = 1
            )).execute()
        }, onResult = { like -> println(like)})
    }

    override fun showDialog() {
        view.showDialog()
    }

    override fun hideDialog() {
        view.hideDialog()
    }

    override fun showError(error: String) {
        view.showError(error)
    }
}