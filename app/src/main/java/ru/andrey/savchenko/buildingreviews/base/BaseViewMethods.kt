package ru.andrey.savchenko.buildingreviews.base

import android.content.Context
import android.support.v4.app.FragmentManager
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.fragments.ProgressFragment

/**
 * Created by savchenko on 28.04.18.
 */
const val PROGRESS = "progress"

interface BaseViewMethods {

//    fun initProgressBar(context: Context, container: ViewGroup):RelativeLayout {
//        val progressLayout = RelativeLayout(context)
//        val params = LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT)
//        params.gravity = Gravity.CENTER
//        progressLayout.layoutParams = params
//
//        val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleLargeInverse)
//        val lp = RelativeLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                72
//        )
//        lp.addRule(RelativeLayout.CENTER_IN_PARENT)
//
//        progressBar.layoutParams = lp
//        progressLayout.addView(progressBar)
//        container.addView(progressLayout)
//        return progressLayout
//    }

    fun showProgressFragment(supportFragmentManager:FragmentManager){
        supportFragmentManager.beginTransaction()
                .add(R.id.container, ProgressFragment(), PROGRESS)
                .commit()
    }

    fun hideProgressFragment(supportFragmentManager:FragmentManager){
        supportFragmentManager.beginTransaction()
                .remove(supportFragmentManager.findFragmentByTag(PROGRESS))
                .commit()
    }
}