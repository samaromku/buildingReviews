package ru.andrey.savchenko.buildingreviews.fragments.addreview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_add_review.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseFragment

/**
 * Created by savchenko on 11.04.18.
 */
class AddReviewFragment:BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_add_review, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rbRating.setOnRatingBarChangeListener { ratingBar, fl, b -> tvRating.text = "$fl из 5" }
    }
}