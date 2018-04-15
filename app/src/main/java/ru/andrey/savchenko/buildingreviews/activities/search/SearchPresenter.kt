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
import java.util.ArrayList

/**
 * Created by savchenko on 10.04.18.
 */
@InjectViewState
class SearchPresenter : BasePresenter<SearchView>() {
    val interActor = SearchInterActor()
    var allCompanies: MutableList<Company>? = null
    val currentCompanies:MutableList<Company> = mutableListOf()


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
            result?.body()?.toMutableList()?.let {
                viewState.setListToAdapter(it)
                allCompanies = it
                currentCompanies.addAll(it)
            }
            viewState.hideDialog()
        }
    }

    fun clickOnPosition(position: Int) {
        currentCompanies.get(position).id.let { viewState.startOneCompanyActivity(it) }
    }

    fun getCompanyList() {
        interActor.getCompanyList()
                .compose(DialogTransformer())
                .subscribe({ list -> viewState.setListToAdapter(list) },
                        { t -> t.printStackTrace() })
    }

    fun searchedList(searchString: String) {
        if (searchString.isEmpty()) {
            currentCompanies.clear()
            allCompanies?.let { currentCompanies.addAll(it) }
            viewState.setListToAdapter(currentCompanies)
            return
        } else {
            val words = searchString.split(" ")

            currentCompanies.clear()
            allCompanies?.let { currentCompanies.addAll(it) }

            for (word in words) {
                val tasks = ArrayList<Company>()
                for (task in currentCompanies) {
                    val sb = StringBuilder()
                    if (task.title != null) {
                        sb.append(task.title.toLowerCase())
                    }
                    if (task.address != null) {
                        sb.append(" ")
                        sb.append(task.address.toLowerCase())
                    }
                    if (task.title != null && task.address != null) {
                        val bodyAddress = sb.toString()
                        if (bodyAddress.contains(word.toLowerCase())) {
                            tasks.add(task)
                        }
                    }

                }
                currentCompanies.clear()
                currentCompanies.addAll(tasks)
            }
            viewState.setListToAdapter(currentCompanies)
        }
    }
}