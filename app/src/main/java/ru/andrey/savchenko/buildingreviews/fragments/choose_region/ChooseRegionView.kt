package ru.andrey.savchenko.buildingreviews.fragments.choose_region

import ru.andrey.savchenko.buildingreviews.entities.Region

/**
 * Created by savchenko on 24.04.18.
 */
interface ChooseRegionView {
    fun setListToAdapter(list:MutableList<Region>)
    fun updateAdapter()
    fun getSelectedRegions(list:MutableList<Region>)
    fun showToast(text:String)
}