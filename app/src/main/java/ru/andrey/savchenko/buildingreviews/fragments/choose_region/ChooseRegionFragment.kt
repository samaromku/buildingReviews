package ru.andrey.savchenko.buildingreviews.fragments.choose_region

import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.bottom_buttons.*
import kotlinx.android.synthetic.main.fragment_choose_region.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.OnItemClickListener
import ru.andrey.savchenko.buildingreviews.entities.Region
import ru.andrey.savchenko.buildingreviews.fragments.choose_region.adapter.RegionAdapter

/**
 * Created by savchenko on 24.04.18.
 */
class ChooseRegionFragment : DialogFragment(), ChooseRegionView {
    lateinit var presenter: ChooseRegionPresenter
    lateinit var regionListener: (list: MutableList<Region>) -> Unit
    var adapter: RegionAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_choose_region, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ChooseRegionPresenter(this)
        presenter.getRegions()
        btnOk.setOnClickListener {
            presenter.getSelectedRegions()
        }
        btnCancel.setOnClickListener { dialog.dismiss() }
    }

    override fun setListToAdapter(list: MutableList<Region>) {
        adapter = RegionAdapter(list, object : OnItemClickListener {
            override fun onclick(position: Int) {
                presenter.clickOnRegion(position)
            }
        })
        rvRegion.layoutManager = LinearLayoutManager(activity)
        rvRegion.adapter = adapter
    }

    override fun onRegionClicked() {
        adapter?.notifyDataSetChanged()
    }

    override fun getSelectedRegions(list: MutableList<Region>) {
        dialog.dismiss()
        regionListener(list)
    }

    override fun showToast(text: String) {
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }
}