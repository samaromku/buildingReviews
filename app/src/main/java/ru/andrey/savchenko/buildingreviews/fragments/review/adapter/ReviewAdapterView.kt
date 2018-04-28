package ru.andrey.savchenko.buildingreviews.fragments.review.adapter

import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse

/**
 * Created by savchenko on 22.04.18.
 */
interface ReviewAdapterView {
    fun showError(error: ErrorResponse)
    fun showDialog()
    fun hideDialog()
    fun updateAdapter()
}