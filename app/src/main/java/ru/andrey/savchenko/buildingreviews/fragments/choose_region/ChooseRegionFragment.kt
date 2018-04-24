package ru.andrey.savchenko.buildingreviews.fragments.choose_region

import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_choose_region.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.OnItemClickListener

/**
 * Created by savchenko on 24.04.18.
 */
class ChooseRegionFragment : DialogFragment(), ChooseRegionView {
    lateinit var presenter: ChooseRegionPresenter
    lateinit var regionListener:(region:String) -> Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_choose_region, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ChooseRegionPresenter(this)
        presenter.getRegions()
    }

    override fun setListToAdapter(list: MutableList<String>) {
        rvRegion.layoutManager = LinearLayoutManager(activity)
        rvRegion.adapter = RegionAdapter(list, object : OnItemClickListener {
            override fun onclick(position: Int) {
                presenter.clickOnRegion(position)
            }
        })
    }

    override fun chooseRegion(region: String) {
        dialog.dismiss()
        regionListener(region)
    }
}