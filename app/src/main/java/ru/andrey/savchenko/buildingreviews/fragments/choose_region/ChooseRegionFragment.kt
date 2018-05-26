package ru.andrey.savchenko.buildingreviews.fragments.choose_region

import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import kotlinx.android.synthetic.main.bottom_buttons.*
import kotlinx.android.synthetic.main.fragment_choose_region.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseAdapter
import ru.andrey.savchenko.buildingreviews.entities.Region
import ru.andrey.savchenko.buildingreviews.fragments.choose_region.adapter.RegionAdapter
import ru.andrey.savchenko.buildingreviews.storage.gone
import ru.andrey.savchenko.buildingreviews.storage.visible

/**
 * Created by savchenko on 24.04.18.
 */
class ChooseRegionFragment : DialogFragment(), ChooseRegionView {
    lateinit var presenter: ChooseRegionPresenter
    lateinit var regionListener: () -> Unit
    var adapter: RegionAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_choose_region, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE);

        presenter = ChooseRegionPresenter(this)

        presenter.getRegions()
        btnAll.setOnClickListener {
            presenter.setAllSelected()
        }
        btnOk.setOnClickListener {
            presenter.getSelectedRegions()
        }
        btnCancel.setOnClickListener { dialog.dismiss() }
    }

    override fun setListToAdapter(list: MutableList<Region>) {
        adapter = RegionAdapter(list, object : BaseAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                presenter.clickOnRegion(position)
            }
        })
        rvRegion.layoutManager = LinearLayoutManager(activity)
        rvRegion.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }

    override fun updateAdapter() {
        adapter?.notifyDataSetChanged()
    }

    override fun getSelectedRegions(list: MutableList<Region>) {
        dialog.dismiss()
        regionListener()
    }

    override fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

    override fun showDialog() {
        progressBar.visible()
    }

    override fun hideDialog() {
        progressBar.gone()
    }

    override fun showError(error: String) {
        showToast(error)
    }
}