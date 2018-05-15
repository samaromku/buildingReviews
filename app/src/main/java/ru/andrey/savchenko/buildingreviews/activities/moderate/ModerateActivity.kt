package ru.andrey.savchenko.buildingreviews.activities.moderate

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import org.koin.android.architecture.ext.viewModel
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseActivity

/**
 * Created by savchenko on 14.05.18.
 */
class ModerateActivity : BaseActivity() {
    lateinit var presenter: ModeratePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moderate)
        initBackButton()
        presenter = ViewModelProviders.of(this)
                .get(ModeratePresenter::class.java)
        presenter.reviews.observe(this, Observer { data ->
            println("activity $data")
        })
        stateWorking()
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
                }
            }
        })
    }
}