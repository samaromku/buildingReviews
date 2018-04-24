package ru.andrey.savchenko.buildingreviews.fragments.choose_region

/**
 * Created by savchenko on 24.04.18.
 */
interface ChooseRegionView {
    fun setListToAdapter(list:MutableList<String>)
    fun chooseRegion(region:String)
}