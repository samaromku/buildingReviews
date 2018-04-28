package ru.andrey.savchenko.buildingreviews.base

import com.arellomobile.mvp.MvpView
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse


interface BaseView:MvpView {
    fun showError(error: ErrorResponse, repeat:() -> Unit)
    /**
     * методо, если ui не надо блочить, к примеру ввод теста в строку и параллельное выполнение запроса
     */
    fun showProgress()
    fun hideProgress()
    fun changeToolbarTitle(title: String)
    /**
     * метод если нужно заблочить ui, к примеру получить данные и больше ничего
     */
    fun showDialog()
    fun hideDialog()
    fun showToast(text:String)
}