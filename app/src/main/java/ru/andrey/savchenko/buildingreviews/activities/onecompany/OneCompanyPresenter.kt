package ru.andrey.savchenko.buildingreviews.activities.onecompany

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.andrey.savchenko.buildingreviews.base.BasePresenterNoMvp
import ru.andrey.savchenko.buildingreviews.base.ViewState
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorRepeat
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.terrakok.cicerone.Router

/**
 * Created by savchenko on 11.04.18.
 */
class OneCompanyPresenter : ViewModel(), BasePresenterNoMvp {
    val state: MutableLiveData<ViewState> = MutableLiveData()
    lateinit var router: Router
    var errorRepeat:ErrorRepeat? = null
    lateinit var key:String

    fun openFragment(key:String){
        router.newRootScreen(key)
    }

    override fun showDialog() {
        state.postValue(ViewState.Loading)
    }

    fun hideDialog(key:String){
        this.key = key
        state.value = ViewState.Loaded
    }

    override fun hideDialog() {
        state.postValue(ViewState.Loaded)
    }

    fun showError(errorRepeat: ErrorRepeat){
        this.errorRepeat = errorRepeat
        state.value = ViewState.ErrorShown
    }

    override fun showError(error: ErrorResponse, repeat: () -> Unit) {
        state.postValue(ViewState.ErrorShown)
    }
}