package ru.andrey.savchenko.buildingreviews.activities.search

import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Response
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler

/**
 * Created by savchenko on 10.04.18.
 */
@InjectViewState
class SearchPresenter : BasePresenter<SearchView>() {
    val interActor = SearchInterActor()
    var list: MutableList<Company>? = null


    fun corCompanyList() {
        launch(UI) {
            viewState.showDialog()
            var result: Response<List<Company>>? = null
            try {
                result = async(CommonPool) {
                    NetworkHandler.getService().corGetCompanies().execute()
                }.await()
            } catch (ex: Exception) {
                ex.printStackTrace()
                viewState.showError(ex.message.toString())
            }
            viewState.hideDialog()
            result?.body()?.toMutableList()?.let {
                viewState.setListToAdapter(it)
                list = it
            }
        }
    }

    fun clickOnPosition(position: Int) {
        list?.get(position)?.id?.let { viewState.startOneCompanyActivity(it) }
    }

    fun getCompanyList() {
        interActor.getCompanyList()
                .compose(DialogTransformer())
                .subscribe({ list -> viewState.setListToAdapter(list) },
                        { t -> t.printStackTrace() })
    }
}