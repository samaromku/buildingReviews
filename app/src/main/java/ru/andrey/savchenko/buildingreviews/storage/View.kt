package ru.andrey.savchenko.buildingreviews.storage

import android.view.View

/**
 * Created by savchenko on 15.05.18.
 */
fun View.visible(){
    visibility = View.VISIBLE
}

fun View.gone(){
    visibility = View.GONE
}