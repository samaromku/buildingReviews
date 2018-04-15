package ru.andrey.savchenko.buildingreviews.fragments.review

import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Response
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.Review
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler

/**
 * Created by Andrey on 13.04.2018.
 */
@InjectViewState
class ReviewPresenter: BasePresenter<ReviewView>() {
    var list: MutableList<Review>? = null

    fun getReviews(companyId:Int){
        launch(UI){
            viewState.showDialog()
            var result: Response<List<Review>>? = null
            try {
                result = async(CommonPool) {
                    NetworkHandler.getService().getReviewsByCompanyId(companyId).execute()
                }.await()
            } catch (ex: Exception) {
                ex.printStackTrace()
                viewState.showError(ex.message.toString())
            }
            result?.body()?.toMutableList()?.let {
                if(it.isEmpty()){
                    viewState.setNoReviewsVisible()
                    return@let
                }
                it.sortByDescending { it.created }
                viewState.setListToAdapter(it)
                list = it
            }
            viewState.hideDialog()
        }


    }
}