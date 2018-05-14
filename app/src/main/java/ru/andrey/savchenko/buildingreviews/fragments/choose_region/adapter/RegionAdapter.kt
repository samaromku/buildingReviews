package ru.andrey.savchenko.buildingreviews.fragments.choose_region.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_region.*
import ru.andrey.savchenko.App
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseAdapter
import ru.andrey.savchenko.buildingreviews.base.BaseViewHolder
import ru.andrey.savchenko.buildingreviews.db.Repository
import ru.andrey.savchenko.buildingreviews.entities.Region

/**
 * Created by savchenko on 24.04.18.
 */
class RegionAdapter(dataList: MutableList<Region>,
                    onItemClickListener: OnItemClickListener) :
        BaseAdapter<Region>(dataList, onItemClickListener) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Region> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_region, parent, false)
        return RegionViewHolder(view)
    }

    class RegionViewHolder(itemView: View) : BaseViewHolder<Region>(itemView) {
        override fun bind(t: Region, clickListener: OnItemClickListener) {
            super.bind(t, clickListener)
            tvTitle.text = t.value
            chbSelected.isChecked = t.selected
            chbSelected.setOnClickListener {
                t.selected = !t.selected
                Repository().dbQueryWrapper({
                    it.regionDao().insertOrUpdate(t)
                })
            }
        }
    }
}