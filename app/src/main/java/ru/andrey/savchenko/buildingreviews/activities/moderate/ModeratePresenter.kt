package ru.andrey.savchenko.buildingreviews.activities.moderate

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.Job
import retrofit2.Response
import ru.andrey.savchenko.buildingreviews.base.BasePresenterNoMvp
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.entities.network.ApiResponse
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.andrey.savchenko.buildingreviews.network.Network
import ru.andrey.savchenko.buildingreviews.storage.Const.Companion.REVIEW_ADDED
import ru.andrey.savchenko.buildingreviews.storage.Const.Companion.REVIEW_DENIED

/**
 * Created by savchenko on 14.05.18.
 */
class ModeratePresenter() : BasePresenterNoMvp, ViewModel() {
    val reviews = MutableLiveData<MutableList<Review>>()
    val coroutines = mutableListOf<Job>()
    val state: MutableLiveData<ViewState> = MutableLiveData()
    var error: ErrorResponse? = null

    init {
        println(ViewState.Created)
        state.value = ViewState.Created
    }


    override fun showDialog() {
        println("presenter state loading")
        state.value = ViewState.Loading
    }

    override fun hideDialog() {
        println("presenter state loaded")
        state.postValue(ViewState.Loaded)
    }

    override fun showError(error: ErrorResponse, repeat: () -> Unit) {
        state.postValue(ViewState.ErrorShown)
        this.error = error
    }

    fun getNotAddedReviews() {
        showDialog()
        var result :Response<ApiResponse<List<Review>>>? = null
        val networkReques = Thread(Runnable {
            result = Network().getService().getNotAddedReviews().execute()
        })

        networkReques.start()

        networkReques.join()
        hideDialog()
        reviews.value = result?.body()?.data?.toMutableList()

//        coroutines.add(corMethod(
//                request = {
//                    Network().getService().getNotAddedReviews().execute()
//                }, onResult = {
//            reviews.value = it.toMutableList()
//        }))
    }

    fun sendAddedAndDeleted() {
        val addedReviews = reviews.value?.filter { it.selected }
        addedReviews?.let {
            for (review in addedReviews) {
                review.state = REVIEW_ADDED
            }
        }
        val deniedReviews = reviews.value?.filter { it.denied }
        deniedReviews?.let {
            for (review in deniedReviews) {
                review.state = REVIEW_DENIED
            }
        }
        val needToChangeReviews = mutableListOf<Review>()
        deniedReviews?.let { needToChangeReviews.addAll(it) }
        addedReviews?.let { needToChangeReviews.addAll(it) }
        coroutines.add(corMethod(
                request = {
                    Network().getService()
                            .addAddedReviews(needToChangeReviews).execute()
                },
                onResult = {
                    reviews.value?.removeAll(needToChangeReviews)
                    reviews.postValue(reviews.value)
                }))
    }

    override fun onCleared() {
        super.onCleared()
        for (cor in coroutines) {
            if (cor.isActive) {
                cor.cancel()
            }
        }
    }

    sealed class ViewState {
        object Created : ViewState()
        object Loading : ViewState()
        object Loaded : ViewState()
        object ErrorShown : ViewState()
    }
}