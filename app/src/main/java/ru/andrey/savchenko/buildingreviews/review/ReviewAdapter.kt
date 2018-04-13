package ru.andrey.savchenko.buildingreviews.review

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_review.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseAdapter
import ru.andrey.savchenko.buildingreviews.base.BaseViewHolder
import ru.andrey.savchenko.buildingreviews.base.OnItemClickListener
import ru.andrey.savchenko.buildingreviews.entities.Review

/**
 * Created by Andrey on 13.04.2018.
 */
class ReviewAdapter(list:MutableList<Review>, onItemClickListener: OnItemClickListener) : BaseAdapter<Review>(list, onItemClickListener) {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<Review> {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    class ReviewViewHolder(itemView: View) : BaseViewHolder<Review>(itemView) {
        override fun bind(t: Review, clickListener: OnItemClickListener) {
            super.bind(t, clickListener)
            tvDescription.text = t.description
        }
    }
}