package ru.andrey.savchenko.buildingreviews.fragments.choose_region

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_region.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseAdapter
import ru.andrey.savchenko.buildingreviews.base.BaseViewHolder
import ru.andrey.savchenko.buildingreviews.base.OnItemClickListener

/**
 * Created by savchenko on 24.04.18.
 */
class RegionAdapter(dataList: MutableList<String>,
                    onItemClickListener: OnItemClickListener) :
        BaseAdapter<String>(dataList, onItemClickListener) {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<String> {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_region, parent, false)
        return RegionViewHolder(view)
    }

    class RegionViewHolder(itemView: View) : BaseViewHolder<String>(itemView) {
        override fun bind(t: String, clickListener: OnItemClickListener) {
            super.bind(t, clickListener)
            tvTitle.text = t
        }
    }
}