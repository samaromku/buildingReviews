package ru.andrey.savchenko.buildingreviews.activities.moderate

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import ru.andrey.savchenko.buildingreviews.base.BasePresenterNoMvp
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.andrey.savchenko.buildingreviews.network.Network
import java.util.concurrent.TimeUnit

/**
 * Created by savchenko on 14.05.18.
 */
class ModeratePresenter() : BasePresenterNoMvp, ViewModel() {
    val reviews = MutableLiveData<MutableList<Review>>()
    val coroutines = mutableListOf<Job>()
    val state: MutableLiveData<ViewState> = MutableLiveData()

    init {
        println(ViewState.Created)
        state.value = ViewState.Created
    }

    sealed class ViewState {
        object Created : ViewState()
        object Loading : ViewState()
        object Loaded : ViewState()
    }

    override fun showDialog() {

    }

    override fun hideDialog() {

    }

    override fun showError(error: ErrorResponse, repeat: () -> Unit) {

    }

    fun getNotAddedReviews() {
        coroutines.add(corMethod(beforeRequest = {
            println("presenter state loading")
            state.value = ViewState.Loading
        }, afterRequest = {
            launch(UI) {
                delay(10000, TimeUnit.MILLISECONDS)
                println("presenter state loaded")
                state.postValue(ViewState.Loaded)
            }
        }, request = {
            Network().getService().getNotAddedReviews().execute()
        }, onResult = {
            reviews.value = it.toMutableList()
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
}