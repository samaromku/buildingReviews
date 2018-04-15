package ru.andrey.savchenko.buildingreviews.fragments.buildings

import entities.Building
import ru.andrey.savchenko.buildingreviews.base.BaseView

/**
 * Created by savchenko on 15.04.18.
 */
interface BuildingView:BaseView {
    fun setListToAdapter(list:MutableList<Building>)
}