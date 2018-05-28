package ru.andrey.savchenko.buildingreviews.activities.onecompany

import ru.andrey.savchenko.buildingreviews.base.BaseMoxyPresenter
import ru.terrakok.cicerone.Router

/**
 * Created by savchenko on 11.04.18.
 */
class OneCompanyPresenter(private val router: Router): BaseMoxyPresenter<OneCompanyView>() {

    fun openFragment(key:String){
        router.newRootScreen(key)
    }

}