package ru.andrey.savchenko.buildingreviews.base

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer

/**
 * Created by savchenko on 18.02.18.
 */
/**
 * layoutcontainer for kotlin extentions
 */
open class BaseViewHolder<T>(override val containerView: View?) :
        RecyclerView.ViewHolder(containerView),
        View.OnClickListener,
        LayoutContainer{
    var clickListener: OnItemClickListener? = null

    open fun bind(t:T, clickListener: OnItemClickListener){
        this.clickListener = clickListener
    }

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        clickListener?.onclick(adapterPosition)
    }
}