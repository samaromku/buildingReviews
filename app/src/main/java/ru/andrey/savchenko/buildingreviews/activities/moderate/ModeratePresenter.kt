package ru.andrey.savchenko.buildingreviews.activities.moderate

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.andrey.savchenko.buildingreviews.base.BasePresenterNoMvp
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.andrey.savchenko.buildingreviews.network.Network

/**
 * Created by savchenko on 14.05.18.
 */
class ModeratePresenter(val network: Network) : BasePresenterNoMvp, ViewModel() {
    val reviews = MutableLiveData<MutableList<Review>>()

    override fun showDialog() {

    }

    override fun hideDialog() {

    }

    override fun showError(error: ErrorResponse, repeat: () -> Unit) {

    }

    fun getNotAddedReviews() {
        corMethod(request = {
            network.getService().getNotAddedReviews().execute()
        }, onResult = {
            reviews.value = it.toMutableList()
        })
    }
}