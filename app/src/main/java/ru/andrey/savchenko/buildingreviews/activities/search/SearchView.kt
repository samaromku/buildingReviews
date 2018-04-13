package ru.andrey.savchenko.buildingreviews.activities.search

import ru.andrey.savchenko.buildingreviews.base.BaseView
import ru.andrey.savchenko.buildingreviews.entities.Company

/**
 * Created by savchenko on 10.04.18.
 */
interface SearchView:BaseView {
    fun setListToAdapter(list:MutableList<Company>)
    fun startOneCompanyActivity(id:Int)
}