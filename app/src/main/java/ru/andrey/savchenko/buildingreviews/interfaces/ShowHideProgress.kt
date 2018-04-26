package ru.andrey.savchenko.buildingreviews.interfaces

/**
 * Created by savchenko on 22.04.18.
 */
interface ShowHideProgress {
    fun showProgress()
    fun hideProgress()
    fun showError(error: String, repeat:() -> Unit)
}