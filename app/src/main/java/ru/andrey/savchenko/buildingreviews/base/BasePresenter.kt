package ru.andrey.savchenko.buildingreviews.base

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer

/**
 * Created by savchenko on 10.04.18.
 */
open class BasePresenter<T:BaseView>: MvpPresenter<T>() {
    inner class ProgressBarTransformer<T> : SingleTransformer<T, T> {
        override fun apply(upstream: Single<T>): Single<T>? {
            return upstream
                    .doOnSubscribe { showProgress() }
                    .doFinally({ hideProgress() })
        }
    }

    fun showProgress() {
        viewState.showprogress()
    }

    fun hideProgress() {
        viewState.hideprogress()
    }

    inner class DialogTransformer<T> : SingleTransformer<T, T> {
        override fun apply(upstream: Single<T>): SingleSource<T> {
            return upstream
                    .doOnSubscribe { showDialog() }
                    .doFinally({ hideDialog() })
        }
    }

    fun showDialog() {
        viewState.showDialog()
    }

    fun hideDialog() {
        viewState.hideDialog()
    }
}