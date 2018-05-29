package ru.andrey.savchenko.buildingreviews.base

import com.arellomobile.mvp.MvpPresenter
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse

/**
 * Created by savchenko on 10.04.18.
 */
open class BaseMoxyPresenter<T : BaseView> : MvpPresenter<T>(), BasePresenterNoMvp {

    fun showProgress() {
        viewState.showProgress()
    }

    fun hideProgress() {
        viewState.hideProgress()
    }

    override fun showDialog() {
        viewState.showDialog()
    }

    override fun hideDialog() {
        viewState.hideDialog()
    }

    override fun showError(error:ErrorResponse, repeat:() -> Unit){
        viewState.showError(error, repeat)
    }
}