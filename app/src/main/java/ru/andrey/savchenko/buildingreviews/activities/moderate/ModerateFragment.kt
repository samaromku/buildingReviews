package ru.andrey.savchenko.buildingreviews.activities.moderate

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_reviews.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseAdapter
import ru.andrey.savchenko.buildingreviews.base.BaseFragment
import ru.andrey.savchenko.buildingreviews.fragments.review.adapter.ReviewAdapter
import ru.andrey.savchenko.buildingreviews.interfaces.ShowHideProgress
import android.arch.lifecycle.Observer
import android.view.*
import android.widget.Toast

/**
 * Created by savchenko on 28.05.18.
 */
class ModerateFragment:BaseFragment(), ShowHideProgress {
    lateinit var presenter: ModeratePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_moderate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        presenter = ViewModelProviders.of(this).get(ModeratePresenter::class.java)
        presenter.reviews.observe(this, Observer { data ->
            data?.let {
                val adapter = ReviewAdapter(it, object : BaseAdapter.OnItemClickListener {
                    override fun onClick(position: Int) {
                        println(position)
                    }
                }, this, true)
                rvReviews.layoutManager = LinearLayoutManager(activity)
                rvReviews.adapter = adapter
            }
        })
        stateWorking()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.send_review, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_send -> { presenter.sendAddedAndDeleted() }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun stateWorking(){
        presenter.state.observe(this, Observer { state ->
            when (state) {
                ModeratePresenter.ViewState.Created -> {
                    println("state created")
                    presenter.getNotAddedReviews()
                }
                ModeratePresenter.ViewState.Loading -> {
                    showProgress()
                    println("state loading")
                }
                ModeratePresenter.ViewState.Loaded -> {
                    hideProgress()
                    println("state loaded")
                }ModeratePresenter.ViewState.ErrorShown -> {
                Toast.makeText(activity, presenter.error?.message, Toast.LENGTH_SHORT).show()
                println("state error")
            }
            }
        })
    }
}