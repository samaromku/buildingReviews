package ru.andrey.savchenko.buildingreviews.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseFragment

/**
 * Created by savchenko on 28.04.18.
 */
class ProgressFragment:BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_progress, container, false)
    }
}