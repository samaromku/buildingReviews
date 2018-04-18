package ru.andrey.savchenko.buildingreviews.fragments.addreview

import com.arellomobile.mvp.InjectViewState
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.storage.Coroutiner
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

        var rating: Int? = null
        if (ratingText.isNotEmpty()) {
            rating = ratingText.split(" из ")[0].toInt()
        }
        if (positive.isEmpty() && negative.isEmpty() && general.isEmpty()) {
            viewState.showToast("Заполните хотя бы одно поле")
        } else if (rating == null) {
            viewState.showToast("Выбирите рейтинг")
        } else {
            val review = Review(id = 0,
                    companyId = companyId,
                    positive = positive,
                    negative = negative,
                    general = general,
                    peopleLike = "0",
                    rating = rating,
                    created = SimpleDateFormat("yyyy.MM.dd").format(Date()),
                    creatorId = Storage.user?.id,
                    userName = Storage.user?.name)

            Coroutiner<Boolean>(viewState).corMethod(
                    request = {NetworkHandler.getService().sendReview(review).execute()},
                    onResult = {
                        println(it)
                        viewState.finishAfterSent()
                    }
            )
        }
    }
}