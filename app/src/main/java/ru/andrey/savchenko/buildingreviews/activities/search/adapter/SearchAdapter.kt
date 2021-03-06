package ru.andrey.savchenko.buildingreviews.activities.search.adapter

import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_company.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseAdapter
import ru.andrey.savchenko.buildingreviews.base.BaseViewHolder
import ru.andrey.savchenko.buildingreviews.base.getViewForHolder
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.storage.Utils.Companion.getImageFullUrl


/**
 * Created by savchenko on 10.04.18.
 */
class SearchAdapter(dataList: MutableList<Company>,
                    onItemClickListener: OnItemClickListener) :
        BaseAdapter<Company>(dataList, onItemClickListener) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Company> {
        return CompanyViewHolder(getViewForHolder(parent, R.layout.item_company))
    }




    fun addToList(list:MutableList<Company>){
        dataList = list.toMutableList()
        notifyDataSetChanged()
    }

    class CompanyViewHolder(itemView: View) : BaseViewHolder<Company>(itemView) {
        override fun bind(t: Company, clickListener: OnItemClickListener) {
            super.bind(t, clickListener)
            tvTitle.text = t.title
            tvDescription.text = t.description

            Picasso.get().load(getImageFullUrl(t.imageUrl))
//                    .transform(CircleTransform())
                    .into(ivLogo)
        }
    }
}