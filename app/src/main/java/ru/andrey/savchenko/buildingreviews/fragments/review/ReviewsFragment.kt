package ru.andrey.savchenko.buildingreviews.fragments.review

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_reviews.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseFragment
import ru.andrey.savchenko.buildingreviews.base.OnItemClickListener
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.fragments.review.adapter.ReviewAdapter
import ru.andrey.savchenko.buildingreviews.interfaces.ShowHideProgress
import ru.andrey.savchenko.buildingreviews.storage.Const

/**
 * Created by Andrey on 13.04.2018.
 */
class ReviewsFragment:BaseFragment(), ReviewView, ShowHideProgress {
    @InjectPresenter
    lateinit var presenter:ReviewPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getReviews(activity.intent.getIntExtra(Const.COMPANY_ID, 0))
    }

    override fun setListToAdapter(list: MutableList<Review>) {
        val adapter = ReviewAdapter(list, object : OnItemClickListener {
            override fun onclick(position: Int) {
                println(position)
            }
        }, this)
        rvReviews.layoutManager = LinearLayoutManager(activity)
        rvReviews.adapter = adapter
    }

    override fun setNoReviewsVisible() {
        tvNoReviews.visibility = View.VISIBLE
    }
}