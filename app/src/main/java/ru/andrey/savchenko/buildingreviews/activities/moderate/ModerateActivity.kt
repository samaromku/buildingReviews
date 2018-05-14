package ru.andrey.savchenko.buildingreviews.activities.moderate

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.Observer
import android.os.Bundle
import org.koin.android.architecture.ext.viewModel
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseActivity

/**
 * Created by savchenko on 14.05.18.
 */
class ModerateActivity : BaseActivity() {
    val presenter: ModeratePresenter by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moderate)
        initBackButton()
        presenter.reviews.observe(this, Observer { data ->
            println("activity $data")
        })
        presenter.getNotAddedReviews()
    }
}