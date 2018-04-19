package ru.andrey.savchenko.buildingreviews.activities.search

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.activities.auth.AuthActivity
import ru.andrey.savchenko.buildingreviews.activities.onecompany.OneCompanyActivity
import ru.andrey.savchenko.buildingreviews.base.BaseActivity
import ru.andrey.savchenko.buildingreviews.base.OnItemClickListener
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.storage.Const.Companion.COMPANY_ID
import ru.andrey.savchenko.buildingreviews.storage.Storage
import java.util.concurrent.TimeUnit


class SearchActivity : BaseActivity(), SearchView, OnItemClickListener {
    val TAG = SearchActivity::class.java.simpleName
    @InjectPresenter
    lateinit var presenter: SearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        initBackButton()

        RxTextView.textChanges(etSearch)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ text -> presenter.searchedList(text.toString()) },
                        { it.printStackTrace() })

        presenter.corCompanyList()
        ivBack.setOnClickListener { backClick() }
        ivClose.setOnClickListener {
            etSearch.setText("")
        }
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
        when (item?.itemId) {
            R.id.action_search -> { openToolbarSearch() }
            R.id.action_exit -> { exit() }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun exit(){
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
        Storage.user = null
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
        search_toolbar.visibility = View.GONE
        hideKeyboard(this, etSearch)
    }

    override fun onBackPressed() {
        if (search_toolbar.visibility == View.VISIBLE) {
            toolbar.visibility = View.VISIBLE
            search_toolbar.visibility = View.GONE
            hideKeyboard(this, findViewById(R.id.etSearch))
        } else {
            super.onBackPressed()
        }
    }
}
