package ru.andrey.savchenko.buildingreviews.fragments.review.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_review.*
import ru.andrey.savchenko.buildingreviews.R
import ru.andrey.savchenko.buildingreviews.base.BaseAdapter
import ru.andrey.savchenko.buildingreviews.base.BaseViewHolder
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.andrey.savchenko.buildingreviews.interfaces.ShowHideProgress
import ru.andrey.savchenko.buildingreviews.storage.Const.Companion.REVIEW_IN_PROGRESS
import ru.andrey.savchenko.buildingreviews.storage.gone
import ru.andrey.savchenko.buildingreviews.storage.visible

/**
 * Created by Andrey on 13.04.2018.
 */
class ReviewAdapter(list: MutableList<Review>,
                    onItemClickListener: OnItemClickListener,
                    val showHideProgress: ShowHideProgress,
                    val moderate: Boolean = false) :
        BaseAdapter<Review>(list, onItemClickListener), ReviewAdapterView {
    val presenter = ReviewAdapterPresenter(this, list)
    lateinit var currentJob: () -> Unit

    override fun showError(error: ErrorResponse) {
        showHideProgress.showError(error, {
            currentJob()
        })
    }

    override fun showDialog() {
        showHideProgress.showProgress()
    }

    override fun hideDialog() {
        showHideProgress.hideProgress()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Review> {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view, presenter)
    }

    inner class ReviewViewHolder(itemView: View, val presenter: ReviewAdapterPresenter) : BaseViewHolder<Review>(itemView) {
        override fun bind(t: Review, clickListener: OnItemClickListener) {
            super.bind(t, clickListener)
            if (moderate) {
                llModeratorCheckBoxes.visible()
            }
            chbAdd.setOnCheckedChangeListener { _, isChecked ->
                t.selected = isChecked
            }
            chbDeny.setOnCheckedChangeListener { _, isChecked ->
                t.denied = isChecked
            }
            if (t.state == REVIEW_IN_PROGRESS) {
                tvInProgress.visible()
            } else {
                tvInProgress.gone()
            }
            tvRating.text = "${t.rating} из 5"
            tvCreatorName.text = t.userName

            setTextViewVisibility(tvPositive, tvPositiveText, t.positive)
            setTextViewVisibility(tvNegative, tvNegativeText, t.negative)
            setTextViewVisibility(tvGeneralEmotion, tvGeneralEmotionText, t.general)


            tvPositive.text = t.positive
            tvNegative.text = t.negative
            tvGeneralEmotion.text = t.general
            tvCreateDate.text = t.created
            tvPeopleLikes.text = t.peopleLike.toString()

            if (t.like != null) {
                when {
                    t.like.state == 1 -> {
                        setAvailable(ivRatingDown, true)
                        setAvailable(ivRatingUp, false)
                    }
                    t.like.state == -1 -> {
                        setAvailable(ivRatingUp, true)
                        setAvailable(ivRatingDown, false)
                    }
                    else -> {
                        setAvailable(ivRatingUp, true)
                        setAvailable(ivRatingDown, true)
                    }
                }
            }

            ivRatingUp.setOnClickListener {
                currentJob = { presenter.sendLike(t.id, 1, adapterPosition) }
                currentJob()

            }
            ivRatingDown.setOnClickListener {
                currentJob = { presenter.sendLike(t.id, -1, adapterPosition) }
                currentJob()
            }
        }

        private fun setAvailable(ivView: ImageView, enabled: Boolean) {
            if (enabled) {
                ivView.setColorFilter(Color.parseColor("#000000"))
            } else {
                ivView.setColorFilter(Color.parseColor("#8e979b"))
            }
            ivView.isEnabled = enabled
        }
    }

    fun setTextViewVisibility(editableTextView: TextView,
                              textViewWithText: TextView,
                              text: String?) {
        if (text == null || text.isEmpty()) {
            textViewWithText.gone()
            editableTextView.gone()
        } else {
            textViewWithText.visible()
            editableTextView.visible()
        }
    }


    override fun updateAdapter() {
        notifyDataSetChanged()
    }
}