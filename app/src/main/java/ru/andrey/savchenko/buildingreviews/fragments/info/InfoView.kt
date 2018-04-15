package ru.andrey.savchenko.buildingreviews.fragments.info

import ru.andrey.savchenko.buildingreviews.base.BaseView

/**
 * Created by savchenko on 15.04.18.
 */
interface InfoView:BaseView {
    fun setLogo(text:String)
    fun setPhone(text:String)
    fun setAddress(text:String)
    fun setSite(text:String)
    fun setDescription(text:String)
}