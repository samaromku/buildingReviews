package ru.andrey.savchenko.buildingreviews.fragments.buildings.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import entities.Building
import kotlinx.android.synthetic.main.item_building.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseAdapter
import ru.andrey.savchenko.buildingreviews.base.BaseViewHolder
import ru.andrey.savchenko.buildingreviews.storage.Utils

/**
 * Created by savchenko on 15.04.18.
 */
class BuildingsAdapter(list: MutableList<Building>, onItemClickListener: OnItemClickListener)
    : BaseAdapter<Building>(list, onItemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Building> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_building, parent, false)
        return BuildingViewHolder(view)
    }

    class BuildingViewHolder(itemView: View) : BaseViewHolder<Building>(itemView) {
        override fun bind(t: Building, clickListener: OnItemClickListener) {
            super.bind(t, clickListener)
            Picasso.get()
                    .load(Utils.getImageBuildingUrl(t.imageUrl))
                    .fit().centerCrop()
                    .into(ivLogo)
            tvTitle.text = t.title
            tvAddress.text = t.address
            tvClassBuilding.text = t.classBuilding
            tvPrice.text = t.priceBuilding
            tvDate.text = t.dateBuilding
        }
    }
}