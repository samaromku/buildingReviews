package ru.andrey.savchenko.buildingreviews.activities.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_company.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseAdapter
import ru.andrey.savchenko.buildingreviews.base.BaseViewHolder
import ru.andrey.savchenko.buildingreviews.base.OnItemClickListener
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.view.CircleTransform


/**
 * Created by savchenko on 10.04.18.
 */
class SearchAdapter(dataList: MutableList<Company>,
                    onItemClickListener: OnItemClickListener) :
        BaseAdapter<Company>(dataList, onItemClickListener) {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<Company> {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_company, parent, false)
        return CompanyViewHolder(view)
    }

    class CompanyViewHolder(itemView: View) : BaseViewHolder<Company>(itemView) {
        override fun bind(t: Company, clickListener: OnItemClickListener) {
            super.bind(t, clickListener)
            tvTitle.text = t.title
            tvDescription.text = t.description

            Picasso.get().load(t.urlImage)
//                    .transform(CircleTransform())
                    .into(ivLogo)
        }
    }
}