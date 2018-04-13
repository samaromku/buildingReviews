package ru.andrey.savchenko.buildingreviews.activities.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseActivity
import ru.andrey.savchenko.buildingreviews.base.OnItemClickListener
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.activities.onecompany.OneCompanyActivity
import ru.andrey.savchenko.buildingreviews.storage.Const.Companion.COMPANY_ID


class SearchActivity : BaseActivity(), SearchView, OnItemClickListener {
    val TAG = SearchActivity::class.java.simpleName
    @InjectPresenter
    lateinit var presenter: SearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)

        presenter.corCompanyList()
        ivBack.setOnClickListener { backClick() }
    }



    override fun setListToAdapter(list: MutableList<Company>) {
        val adapter = SearchAdapter(list,
                this as OnItemClickListener)
        rvCompanies.layoutManager = LinearLayoutManager(this)
        rvCompanies.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_search -> {openToolbarSearch()}

        }
        return super.onOptionsItemSelected(item)
    }

    private fun openToolbarSearch() {
        toolbar.visibility = View.GONE
        search_toolbar.visibility = View.VISIBLE
        showKeyboard(this, etSearch)
    }

    override fun onclick(position: Int) {
        presenter.clickOnPosition(position)
    }

    override fun startOneCompanyActivity(id: Int) {
        startActivity(Intent(this, OneCompanyActivity::class.java)
                .putExtra(COMPANY_ID, id))
    }

    private fun backClick() {
        toolbar.visibility = View.VISIBLE
        search_toolbar.setVisibility(View.GONE)
        hideKeyboard(this, etSearch)
    }

    override fun onBackPressed() {
        if (search_toolbar.visibility == View.VISIBLE) {
            toolbar.visibility = View.VISIBLE
            search_toolbar.visibility = View.GONE
            hideKeyboard(this, findViewById(R.id.etSearch))
        }else {
            super.onBackPressed()
        }
    }
}
