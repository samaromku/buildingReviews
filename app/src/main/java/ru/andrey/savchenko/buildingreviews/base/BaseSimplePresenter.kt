package ru.andrey.savchenko.buildingreviews.base

import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse

/**
 * Created by savchenko on 27.05.18.
 */
abstract class BaseSimplePresenter(var baseView: BaseView?):BasePresenterNoMvp{

    abstract fun attachView(view: BaseView)

    abstract fun detachView()

    override fun showDialog() {
        baseView?.showDialog()
    }

    override fun hideDialog() {
        baseView?.hideDialog()
    }

    override fun showError(error: ErrorResponse, repeat: () -> Unit) {
        baseView?.showError(error, repeat)
    }

}