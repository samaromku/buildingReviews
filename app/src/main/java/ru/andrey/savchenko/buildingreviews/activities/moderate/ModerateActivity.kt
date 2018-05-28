package ru.andrey.savchenko.buildingreviews.activities.moderate

import android.os.Bundle
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseActivity
import ru.andrey.savchenko.buildingreviews.interfaces.ShowHideProgress

/**
 * Created by savchenko on 14.05.18.
 */
class ModerateActivity : BaseActivity(), ShowHideProgress {
    lateinit var presenter: ModeratePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moderate)
        initBackButton()
        supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ModerateFragment(), "moderate")
                    .commit()
//        presenter = ViewModelProviders.of(this)
//                .get(ModeratePresenter::class.java)
//        presenter.reviews.observe(this, Observer { data ->
//            data?.let {
//                val adapter = ReviewAdapter(it, object : BaseAdapter.OnItemClickListener {
//                    override fun onClick(position: Int) {
//                        println(position)
//                    }
//                }, this, true)
//                rvReviews.layoutManager = LinearLayoutManager(this)
//                rvReviews.adapter = adapter
//            }
//        })
//        stateWorking()
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.send_review, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//
//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        when(item?.itemId){
//            R.id.action_send -> { presenter.sendAddedAndDeleted() }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    private fun stateWorking(){
//        presenter.state.observe(this, Observer { state ->
//            when (state) {
//                ModeratePresenter.ViewState.Created -> {
//                    println("state created")
//                    presenter.getNotAddedReviews()
//                }
//                ModeratePresenter.ViewState.Loading -> {
//                    showProgress()
//                    println("state loading")
//                }
//                ModeratePresenter.ViewState.Loaded -> {
//                    hideProgress()
//                    println("state loaded")
//                }ModeratePresenter.ViewState.ErrorShown -> {
//                    Toast.makeText(this, presenter.error?.message, Toast.LENGTH_SHORT).show()
//                    println("state error")
//                }
//            }
//        })
//    }
}