package ru.andrey.savchenko.buildingreviews.base

import android.support.v7.widget.RecyclerView

/**
 * Created by savchenko on 18.02.18.
 */
abstract class BaseAdapter <T> (
        private val dataList: MutableList<T>,
        private val clickListener: OnItemClickListener): RecyclerView.Adapter<BaseViewHolder<T>>() {

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bind(dataList.get(position), clickListener)
    }

    fun removeItem(){
        notifyDataSetChanged()
    }
}