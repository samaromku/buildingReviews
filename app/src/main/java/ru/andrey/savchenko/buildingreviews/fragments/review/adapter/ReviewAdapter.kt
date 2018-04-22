package ru.andrey.savchenko.buildingreviews.fragments.review.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_review.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseAdapter
import ru.andrey.savchenko.buildingreviews.base.BaseViewHolder
import ru.andrey.savchenko.buildingreviews.base.OnItemClickListener
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.interfaces.ShowHideProgress

/**
 * Created by Andrey on 13.04.2018.
 */
class ReviewAdapter(list: MutableList<Review>,
                    onItemClickListener: OnItemClickListener,
                    val showHideProgress: ShowHideProgress) :
        BaseAdapter<Review>(list, onItemClickListener), ReviewAdapterView {
    val presenter = ReviewAdapterPresenter(this, list)

    override fun showError(error: String) {
        showHideProgress.showError(error)
    }

    override fun showDialog() {
        showHideProgress.showProgress()
    }

    override fun hideDialog() {
        showHideProgress.hideProgress()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<Review> {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view, presenter)
    }

    class ReviewViewHolder(itemView: View, val presenter: ReviewAdapterPresenter) : BaseViewHolder<Review>(itemView) {
        override fun bind(t: Review, clickListener: OnItemClickListener) {
            super.bind(t, clickListener)
            tvRating.text = "${t.rating} из 5"
            tvCreatorName.text = t.userName
            if (t.positive == null || t.positive.isEmpty()) {
                tvPositiveText.visibility = View.GONE
                tvPositive.visibility = View.GONE
            } else {
                tvPositiveText.visibility = View.VISIBLE
                tvPositive.visibility = View.VISIBLE
            }
            if (t.negative == null || t.negative.isEmpty()) {
                tvNegativeText.visibility = View.GONE
                tvNegative.visibility = View.GONE
            } else {
                tvNegativeText.visibility = View.VISIBLE
                tvNegative.visibility = View.VISIBLE
            }
            if (t.general == null || t.general.isEmpty()) {
                tvGeneralEmotionText.visibility = View.GONE
                tvGeneralEmotion.visibility = View.GONE
            } else {
                tvGeneralEmotionText.visibility = View.VISIBLE
                tvGeneralEmotion.visibility = View.VISIBLE
            }
            tvPositive.text = t.positive
            tvNegative.text = t.negative
            tvGeneralEmotion.text = t.general
            tvCreateDate.text = t.created
            if (t.peopleLike.isEmpty()) {
                tvPeopleLikes.text = "0"
            } else {
                tvPeopleLikes.text = (t.peopleLike).toInt().toString()
            }

            ivRatingUp.setOnClickListener {
                presenter.sendLike(t.id, 1, adapterPosition)

            }
            ivRatingDown.setOnClickListener {
                presenter.sendLike(t.id, -1, adapterPosition)
            }

        }
    }

    override fun updateAdapter() {
        notifyDataSetChanged()
    }
}