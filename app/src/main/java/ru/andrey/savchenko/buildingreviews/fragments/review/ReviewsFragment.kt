package ru.andrey.savchenko.buildingreviews.fragments.review

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.fragment_reviews.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.activities.onecompany.OneCompanyPresenter
import ru.andrey.savchenko.buildingreviews.base.BaseAdapter
import ru.andrey.savchenko.buildingreviews.base.BaseFragment
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.fragments.info.InfoPresenter
import ru.andrey.savchenko.buildingreviews.fragments.review.adapter.ReviewAdapter
import ru.andrey.savchenko.buildingreviews.interfaces.ShowHideProgress
import ru.andrey.savchenko.buildingreviews.storage.Const
import ru.andrey.savchenko.buildingreviews.storage.gone
import ru.andrey.savchenko.buildingreviews.storage.visible

/**
 * Created by Andrey on 13.04.2018.
 */
class ReviewsFragment : BaseFragment(), ReviewView, ShowHideProgress {
    lateinit var presenter: ReviewPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ViewModelProviders.of(this).get(ReviewPresenter::class.java)
        activity?.let {
            presenter.routerPresenter = ViewModelProviders.of(it).get(OneCompanyPresenter::class.java)
        }

        presenter.list.observe(this, Observer {
            it?.let {
                if (checkListEmpty(it)) {
                    setNoReviewsVisible()
                } else {
                    it.sortByDescending { it.created }
                    setListToAdapter(it)
                }
            }

        })
        activity?.intent?.getIntExtra(Const.COMPANY_ID, 0)?.let {
            presenter.getReviews(it)
        }
    }

    private fun checkListEmpty(list: List<Review>): Boolean = list.isEmpty()

    override fun setListToAdapter(list: MutableList<Review>) {
        val adapter = ReviewAdapter(list, object : BaseAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                println(position)
            }
        }, this)
        rvReviews.layoutManager = LinearLayoutManager(activity)
        rvReviews.adapter = adapter
    }

    override fun setNoReviewsVisible() {
        tvNoReviews.visible()
        rvReviews.gone()
    }

}