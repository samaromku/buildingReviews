package ru.andrey.savchenko.buildingreviews.activities.onecompany

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseActivity

/**
 * Created by savchenko on 11.04.18.
 */
class OneCompanyActivity: BaseActivity(), OneCompanyView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_company)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_favorites -> {}
                R.id.action_schedules -> {}
                R.id.action_music -> {}
                R.id.add_new -> {}
            }
            true
        }

    }
}