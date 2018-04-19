package ru.andrey.savchenko.buildingreviews.activities.search

import com.arellomobile.mvp.InjectViewState
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.entities.network.ApiResponse
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import java.util.*

/**
 * Created by savchenko on 10.04.18.
 */
@InjectViewState
class SearchPresenter : BasePresenter<SearchView>() {
    var allCompanies: MutableList<Company>? = null
    val currentCompanies: MutableList<Company> = mutableListOf()


    fun corCompanyList() {
        corMethod<List<Company>>(
                request = { NetworkHandler.getService().corGetCompanies().execute() },
                onResult = {
                    it.toMutableList().let {
                        viewState.setListToAdapter(it)
                        allCompanies = it
                        currentCompanies.addAll(it)
                    }
                }
        )
    }

    fun clickOnPosition(position: Int) {
        currentCompanies.get(position).id.let { viewState.startOneCompanyActivity(it) }
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