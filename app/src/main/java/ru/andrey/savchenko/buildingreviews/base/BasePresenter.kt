package ru.andrey.savchenko.buildingreviews.base

import com.arellomobile.mvp.MvpPresenter

/**
 * Created by savchenko on 10.04.18.
 */
open class BasePresenter<T:BaseView>: MvpPresenter<T>() {
}