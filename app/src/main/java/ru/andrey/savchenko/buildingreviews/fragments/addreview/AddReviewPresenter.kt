package ru.andrey.savchenko.buildingreviews.fragments.addreview

import com.arellomobile.mvp.InjectViewState
import entities.Building
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Response
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.Like
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
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

        var rating:Int? = null
        if(ratingText.isNotEmpty()){
            rating = ratingText.split(" из ")[0].toInt()
        }
        if (positive.isEmpty() && negative.isEmpty() && general.isEmpty()) {
            viewState.showToast("Заполните хотя бы одно поле")
        } else if (rating==null) {
            viewState.showToast("Выбирите рейтинг")
        } else {
            //todo change userName on real
            val review = Review(id = 0,
                    companyId = companyId,
                    positive = positive,
                    negative = negative,
                    general = general,
                    peopleLike = "0",
                    rating = rating,
                    created = SimpleDateFormat("yyyy.MM.dd").format(Date()),
                    creatorId = 0,
                    userName = "Admin")
            launch(UI) {
                viewState.showDialog()
                var result: Response<Boolean>? = null
                try {
                    result = async(CommonPool) {
                        NetworkHandler.getService().sendReview(review).execute()
                    }.await()
                } catch (ex: Exception) {
                    ex.printStackTrace()
                    viewState.showError(ex.message.toString())
                }
                result?.body()?.let {
                    println(it)
                    viewState.finishAfterSent()
                }
                viewState.hideDialog()
            }
        }
    }
}