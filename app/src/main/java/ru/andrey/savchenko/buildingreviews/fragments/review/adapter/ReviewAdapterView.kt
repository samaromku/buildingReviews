package ru.andrey.savchenko.buildingreviews.fragments.review.adapter

import ru.andrey.savchenko.buildingreviews.base.BaseView

/**
 * Created by savchenko on 22.04.18.
 */
interface ReviewAdapterView {
    fun showError(error: String)
    fun showDialog()
    fun hideDialog()
    fun updateAdapter()
}