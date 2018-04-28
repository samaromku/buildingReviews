package ru.andrey.savchenko.buildingreviews.interfaces

import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse

/**
 * Created by savchenko on 22.04.18.
 */
interface ShowHideProgress {
    fun showProgress()
    fun hideProgress()
    fun showError(error: ErrorResponse, repeat:() -> Unit)
}