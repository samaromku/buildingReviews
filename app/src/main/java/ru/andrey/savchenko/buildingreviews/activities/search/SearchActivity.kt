package ru.andrey.savchenko.buildingreviews.activities.search

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
import ru.andrey.savchenko.buildingreviews.activities.moderate.ModerateActivity
import ru.andrey.savchenko.buildingreviews.activities.onecompany.OneCompanyActivity
import ru.andrey.savchenko.buildingreviews.base.BaseActivity
import ru.andrey.savchenko.buildingreviews.base.BaseAdapter
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.fragments.choose_region.ChooseRegionFragment
import ru.andrey.savchenko.buildingreviews.storage.Const.Companion.COMPANY_ID
import ru.andrey.savchenko.buildingreviews.storage.Storage
import ru.andrey.savchenko.buildingreviews.storage.gone
import ru.andrey.savchenko.buildingreviews.storage.visible
import java.util.concurrent.TimeUnit


class SearchActivity : BaseActivity(), SearchView, BaseAdapter.OnItemClickListener {
    val TAG = SearchActivity::class.java.simpleName
    @InjectPresenter
    lateinit var presenter: SearchPresenter
    var adapter: SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)
        initBackButton()
        getLocation()

        RxTextView.textChanges(etSearch)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ text -> presenter.searchedList(text.toString()) },
                        { it.printStackTrace() })
        presenter.getCompanyList(0)
        ivBack.setOnClickListener { backClick() }
        ivClose.setOnClickListener {
            etSearch.setText("")
        }

        adapter = SearchAdapter(mutableListOf(), this as BaseAdapter.OnItemClickListener)
        val layoutManager = LinearLayoutManager(this)
        rvCompanies.layoutManager = layoutManager
        rvCompanies.adapter = adapter
        rvCompanies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= rvCompanies.adapter.itemCount) {
                    if (progressBar.visibility == View.GONE) {
                        presenter.getCompanyList(rvCompanies.adapter.itemCount)
                    }
                }
            }
        })
    }

    private fun getLocation() {
        val REQUEST_LOCATION_PERMISSIONS = 100
        val listener = object : LocationListener {
            override fun onLocationChanged(p0: Location?) {
                p0?.let { presenter.getAddress(it) }
            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                println("changed")
            }

            override fun onProviderEnabled(p0: String?) {
                println("enabled")
            }

            override fun onProviderDisabled(p0: String?) {
                println("disbled")
            }
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_LOCATION_PERMISSIONS)
        } else {
            val lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (lm.allProviders.contains(LocationManager.NETWORK_PROVIDER)) {
                lm.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, listener, null)
            }
            if (lm.allProviders.contains(LocationManager.GPS_PROVIDER)) {
                lm.requestSingleUpdate(LocationManager.GPS_PROVIDER, listener, null)
            }
        }
    }

    override fun showProgress() {
        progressBar.visible()
    }

    override fun hideProgress() {
        progressBar.gone()
    }

    override fun setListToAdapter(list: MutableList<Company>) {
        adapter?.addToList(list)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item = menu?.findItem(R.id.action_admin)
        item?.isVisible = Storage.user?.login == "test"
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_search -> {
                openToolbarSearch()
            }
            R.id.action_filter -> {
                filter()
            }
            R.id.action_admin -> { admin() }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun filter() {
        val chooseRegion = ChooseRegionFragment()
        chooseRegion.regionListener = { presenter.uploadNewList() }
        chooseRegion.show(fragmentManager, "choose_region")
    }

    override fun updateAdapter() {
        adapter?.notifyDataSetChanged()
    }

    private fun admin() {
        startActivity(Intent(this, ModerateActivity::class.java))
    }

    private fun openToolbarSearch() {
        toolbar.gone()
        search_toolbar.visible()
        showKeyboard(this, etSearch)
    }

    override fun onClick(position: Int) {
        presenter.clickOnPosition(position)
    }

    override fun startOneCompanyActivity(id: Int) {
        startActivity(Intent(this, OneCompanyActivity::class.java)
                .putExtra(COMPANY_ID, id))
    }

    private fun backClick() {
        toolbar.visible()
        search_toolbar.gone()
        hideKeyboard(this, etSearch)
    }

    override fun onBackPressed() {
        if (search_toolbar.visibility == View.VISIBLE) {
            toolbar.visible()
            search_toolbar.gone()
            hideKeyboard(this, findViewById(R.id.etSearch))
        } else {
            super.onBackPressed()
        }
    }
}
