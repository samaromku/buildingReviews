package ru.andrey.savchenko.buildingreviews.fragments.buildings

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import entities.Building
import kotlinx.android.synthetic.main.fragment_buildings.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseFragment
import ru.andrey.savchenko.buildingreviews.base.OnItemClickListener
import ru.andrey.savchenko.buildingreviews.fragments.buildings.adapter.BuildingsAdapter
import ru.andrey.savchenko.buildingreviews.storage.Const

/**
 * Created by savchenko on 11.04.18.
 */
class BuildingsFragment : BaseFragment(), BuildingView {
    @InjectPresenter
    lateinit var presenter: BuildingPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_buildings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.intent?.getIntExtra(Const.COMPANY_ID, 0)?.let { presenter.getBuildingsByCompanyId(it) }
    }

    override fun setListToAdapter(list: MutableList<Building>) {
        rvBuildings.layoutManager = LinearLayoutManager(activity)
        val adapter = BuildingsAdapter(list, object : OnItemClickListener {
            override fun onclick(position: Int) {
                println(position)
            }
        })
        rvBuildings.adapter = adapter
    }

    override fun setTextEmptyBuildings(text: String) {
        tvNoBuildings.visibility = View.VISIBLE
        tvNoBuildings.text = text
    }
}