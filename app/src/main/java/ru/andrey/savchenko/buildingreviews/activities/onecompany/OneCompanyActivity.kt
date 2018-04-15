package ru.andrey.savchenko.buildingreviews.activities.onecompany

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_one_company.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseActivity
import ru.andrey.savchenko.buildingreviews.base.BaseFragment
import ru.andrey.savchenko.buildingreviews.fragments.addreview.AddReviewFragment
import ru.andrey.savchenko.buildingreviews.fragments.info.InfoCompanyFragment
import ru.andrey.savchenko.buildingreviews.fragments.buildings.BuildingsFragment
import ru.andrey.savchenko.buildingreviews.fragments.review.ReviewsFragment

/**
 * Created by savchenko on 11.04.18.
 */
class OneCompanyActivity : BaseActivity(), OneCompanyView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_company)

        initBackButton()


        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_info -> {
                    replaceFragment(InfoCompanyFragment(), "info")
                }
                R.id.action_reviews -> {
                    replaceFragment(ReviewsFragment(), "reviews")
                }
                R.id.action_projects -> {
                    replaceFragment(BuildingsFragment(), "projects")
                }
                R.id.add_review -> {
                    replaceFragment(AddReviewFragment(), "add_review")
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        bottom_navigation.selectedItemId = R.id.action_info
    }

    private fun replaceFragment(baseFragment: BaseFragment, tag: String) {
        val currentFragment = supportFragmentManager.findFragmentByTag(tag)
        if(currentFragment!=null && currentFragment.isVisible){
            return
        }
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction
                .replace(R.id.container, baseFragment, tag)
                .commit()
    }
}