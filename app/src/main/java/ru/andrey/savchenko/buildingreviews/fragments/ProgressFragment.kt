package ru.andrey.savchenko.buildingreviews.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import com.labo.kaji.fragmentanimations.CubeAnimation
import ru.andrey.savchenko.buildingreviews.R

/**
 * Created by savchenko on 28.04.18.
 */
class ProgressFragment:Fragment() {

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation {
        return CubeAnimation.create(CubeAnimation.LEFT, enter, 500)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_progress, container, false)
    }
}