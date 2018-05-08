package ru.andrey.savchenko.buildingreviews.fragments.addreview

import com.arellomobile.mvp.InjectViewState
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.storage.Const.Companion.CHOOSE_RATING
import ru.andrey.savchenko.buildingreviews.storage.Const.Companion.FILL_ONE_FIELD
import ru.andrey.savchenko.buildingreviews.storage.Storage
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by savchenko on 15.04.18.
 */
@InjectViewState
class AddReviewPresenter : BasePresenter<AddReviewView>() {
    fun addReview(
            companyId: Int,
            ratingText: String,
            positive: String,
            negative: String,
            general: String) {

        val rating: Int? = getRatingText(ratingText)
        if (positive.isEmpty() && negative.isEmpty() && general.isEmpty()) {
            viewState.showToast(FILL_ONE_FIELD)
        } else if (rating == null) {
            viewState.showToast(CHOOSE_RATING)
        } else {
            val review = Review(id = 0,
                    companyId = companyId,
                    positive = positive,
                    negative = negative,
                    general = general,
                    peopleLike = 0,
                    rating = rating,
                    created = SimpleDateFormat("yyyy.MM.dd").format(Date()),
                    creatorId = Storage.user?.id,
                    userName = Storage.user?.name)

            corMethod(
                    request = { NetworkHandler.getService().sendReview(review).execute() },
                    onResult = {
                        println(it)
                        viewState.finishAfterSent()
                    }
            )
        }
    }

    private fun getRatingText(ratingText: String): Int? {
        if (ratingText.isNotEmpty()) {
            return ratingText.split(" из ")[0].toInt()
        }
        return null
    }
}