package ru.andrey.savchenko.buildingreviews.base

/**
 * Created by savchenko on 29.05.18.
 */
sealed class ViewState {
    object Loading : ViewState()
    object Loaded : ViewState()
    object ErrorShown : ViewState()
}