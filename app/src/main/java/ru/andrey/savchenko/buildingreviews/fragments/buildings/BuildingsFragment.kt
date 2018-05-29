package ru.andrey.savchenko.buildingreviews.fragments.buildings

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import entities.Building
import kotlinx.android.synthetic.main.fragment_buildings.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.activities.onecompany.OneCompanyPresenter
import ru.andrey.savchenko.buildingreviews.base.BaseAdapter
import ru.andrey.savchenko.buildingreviews.base.BaseFragment
import ru.andrey.savchenko.buildingreviews.fragments.buildings.adapter.BuildingsAdapter
import ru.andrey.savchenko.buildingreviews.storage.Const
import ru.andrey.savchenko.buildingreviews.storage.visible

/**
 * Created by savchenko on 11.04.18.
 */
class BuildingsFragment : BaseFragment(), BuildingView {

    lateinit var presenter: BuildingPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_buildings, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ViewModelProviders.of(this).get(BuildingPresenter::class.java)
        activity?.let {
            presenter.routerPresenter = ViewModelProviders.of(it).get(OneCompanyPresenter::class.java)
        }

        presenter.list.observe(this, Observer {
            if (it != null && it.isNotEmpty()) {
                setListToAdapter(it)
            } else {
                setTextEmptyBuildings(Const.NO_DATA_ABOUT_PROJECTS)
            }
        })
        activity?.intent?.getIntExtra(Const.COMPANY_ID, 0)?.let {
            presenter.getBuildingsByCompanyId(it)
        }
    }


    override fun setListToAdapter(list: MutableList<Building>) {
        rvBuildings.layoutManager = LinearLayoutManager(activity)
        val adapter = BuildingsAdapter(list, object : BaseAdapter.OnItemClickListener {
            override fun onClick(position: Int) {
                println(position)
            }
        })
        rvBuildings.adapter = adapter
    }

    override fun setTextEmptyBuildings(text: String) {
        tvNoBuildings.visible()
        tvNoBuildings.text = text
    }
}