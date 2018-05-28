package ru.andrey.savchenko.buildingreviews.activities.onecompany

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_one_company.*
import ru.andrey.savchenko.App
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.activities.auth.AuthFragment
import ru.andrey.savchenko.buildingreviews.activities.registry.RegisterFragment
import ru.andrey.savchenko.buildingreviews.base.BaseActivity
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorRepeat
import ru.andrey.savchenko.buildingreviews.fragments.ErrorFragment
import ru.andrey.savchenko.buildingreviews.fragments.ProgressFragment
import ru.andrey.savchenko.buildingreviews.fragments.addreview.AddReviewFragment
import ru.andrey.savchenko.buildingreviews.fragments.buildings.BuildingsFragment
import ru.andrey.savchenko.buildingreviews.fragments.info.InfoCompanyFragment
import ru.andrey.savchenko.buildingreviews.fragments.review.ReviewsFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.SupportFragmentNavigator

/**
 * Created by savchenko on 11.04.18.
 */
const val INFO = "info"
const val BUILDINGS = "buildings"
const val REVIEWS = "reviews"
const val ADD_REVIEW = "add_review"
const val AUTH = "auth"
const val ERROR = "error"
const val PROGRESS = "progress"
const val REGISTER = "register"

class OneCompanyActivity : BaseActivity(), OneCompanyView {
    lateinit var presenter: OneCompanyPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_company)
        presenter = OneCompanyPresenter(App.cicerone.router)
        initBackButton()


        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_info -> {
//                    checkCurrentFragmentVisible(INFO, App.cicerone)
                    presenter.openFragment(INFO)
                }
                R.id.action_reviews -> {
                    presenter.openFragment(REVIEWS)
                }
                R.id.action_projects -> {
                    presenter.openFragment(BUILDINGS)
                }
                R.id.add_review -> {
                    presenter.openFragment(ADD_REVIEW)
                }
            }
            true
        }
    }

//    private fun replaceFragment(baseFragment: BaseFragment, tag: String) {
//        if (!checkCurrentFragmentVisible(tag)) {
//            supportFragmentManager.beginTransaction()
//                    .replace(R.id.container, baseFragment, tag)
//                    .commit()
//        }
//    }

    private fun checkCurrentFragmentVisible(tag: String, myFragmentManager: FragmentManager): Boolean {
        println(myFragmentManager)
        val currentFragment = myFragmentManager.findFragmentByTag(tag)
        return currentFragment != null && currentFragment.isVisible
    }

    private fun checkVisible(myFragmentManager: FragmentManager): Boolean {
        val fragment = myFragmentManager.findFragmentById(R.id.container)
        return (fragment is InfoCompanyFragment)
    }

    override fun onResume() {
        super.onResume()
        bottom_navigation.selectedItemId = R.id.action_info
        App.cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.cicerone.navigatorHolder.removeNavigator()
    }


    val navigator: Navigator = object : SupportFragmentNavigator(
            supportFragmentManager,
            R.id.container) {
        override fun exit() {
            finish()
        }

        override fun createFragment(screenKey: String?, data: Any?): Fragment {
            when (screenKey) {
                INFO -> {
                    return InfoCompanyFragment()
                }
                BUILDINGS -> {
                    return BuildingsFragment()
                }
                REVIEWS -> {
                    return ReviewsFragment()
                }
                ADD_REVIEW -> {
                    return AddReviewFragment()
                }
                AUTH -> {
                    return AuthFragment()
                }
                ERROR -> {
                    val errorFragment = ErrorFragment()
                    if (data is ErrorRepeat) {
                        errorFragment.errorRepeat = data
                    }
                    return errorFragment
                }
                PROGRESS -> {
                    return ProgressFragment()
                }
                REGISTER -> {
                    return RegisterFragment()
                }
                else -> {
                    throw Exception("no key")
                }
            }
        }

        override fun showSystemMessage(message: String?) {
            Toast.makeText(this@OneCompanyActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
}