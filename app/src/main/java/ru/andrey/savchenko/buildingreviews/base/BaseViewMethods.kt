package ru.andrey.savchenko.buildingreviews.base

import android.support.v4.app.FragmentManager
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.andrey.savchenko.buildingreviews.fragments.ErrorFragment
import ru.andrey.savchenko.buildingreviews.fragments.ProgressFragment

/**
 * Created by savchenko on 28.04.18.
 */
const val PROGRESS = "progress"
const val ERROR = "error"

interface BaseViewMethods {

    fun showProgressFragment(supportFragmentManager:FragmentManager){
        supportFragmentManager.beginTransaction()
                .add(R.id.container, ProgressFragment(), PROGRESS)
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .commit()
    }

    fun hideProgressFragment(supportFragmentManager:FragmentManager){
        supportFragmentManager.beginTransaction()
                .remove(supportFragmentManager.findFragmentByTag(PROGRESS))
//                .setCustomAnimations(R.animator.slide_in_right, R.animator.slide_in_left)
                .commit()
    }

    fun showErrorFragmentError(error: ErrorResponse,
                               repeat: () -> Unit,
                               supportFragmentManager: FragmentManager){
        val errorFragment = ErrorFragment()
        errorFragment.error = "Код: ${error.code} \nОшибка: ${error.message}"
        errorFragment.repeat = {
            supportFragmentManager.beginTransaction()
                    .remove(supportFragmentManager.findFragmentByTag(ERROR))
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .commit()
            repeat()
        }
        supportFragmentManager.beginTransaction()
                .add(R.id.container, errorFragment, ERROR)
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .commit()
    }
}