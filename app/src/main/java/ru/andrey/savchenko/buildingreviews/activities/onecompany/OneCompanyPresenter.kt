package ru.andrey.savchenko.buildingreviews.activities.onecompany

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.andrey.savchenko.buildingreviews.base.BaseMoxyPresenter
import ru.andrey.savchenko.buildingreviews.base.BasePresenterNoMvp
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.terrakok.cicerone.Router

/**
 * Created by savchenko on 11.04.18.
 */
class OneCompanyPresenter : ViewModel(), BasePresenterNoMvp {
    val state: MutableLiveData<ViewState> = MutableLiveData()
    lateinit var router: Router

    fun openFragment(key:String){
        router.newRootScreen(key)
    }

    override fun showDialog() {
        state.postValue(ViewState.Loading)
    }

    override fun hideDialog() {
        state.postValue(ViewState.Loaded)
    }

    override fun showError(error: ErrorResponse, repeat: () -> Unit) {
        state.postValue(ViewState.ErrorShown)
    }

    sealed class ViewState {
        object Loading : ViewState()
        object Loaded : ViewState()
        object ErrorShown : ViewState()
    }
}