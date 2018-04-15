package ru.andrey.savchenko.buildingreviews.fragments.review

import ru.andrey.savchenko.buildingreviews.base.BaseView
import ru.andrey.savchenko.buildingreviews.entities.Review

/**
 * Created by Andrey on 13.04.2018.
 */
interface ReviewView:BaseView {
    fun setListToAdapter(list:MutableList<Review>)
}