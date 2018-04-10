package ru.andrey.savchenko.buildingreviews.base

import com.arellomobile.mvp.MvpView


interface BaseView:MvpView {
    fun showError(error: String)
    /**
     * методо, если ui не надо блочить, к примеру ввод теста в строку и параллельное выполнение запроса
     */
    fun showprogress()
    fun hideprogress()
    fun changeToolbarTitle(title: String)
    /**
     * метод если нужно заблочить ui, к примеру получить данные и больше ничего
     */
    fun showDialog()
    fun hideDialog()
    fun showToast(text:String)
}