package ru.andrey.savchenko.buildingreviews.activities.search

import com.arellomobile.mvp.InjectViewState
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.entities.ConstDict
import ru.andrey.savchenko.buildingreviews.entities.network.ApiResponse
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.storage.Storage
import java.util.*

/**
 * Created by savchenko on 10.04.18.
 */
@InjectViewState
class SearchPresenter : BasePresenter<SearchView>() {
    val allCompanies: MutableList<Company> = mutableListOf()
    val currentCompanies: MutableList<Company> = mutableListOf()


    fun corCompanyList(itemCount: Int) {
        val start: Int = itemCount
        var end: Int = start + 10
        Storage.getValueConst("itemCount")?.let {
            if(end >=it.toInt()){
                end = it.toInt()
            }
        }
        corMethod<List<Company>>(
                beforeRequest = { showProgress() },
                afterRequest = { hideProgress() },
                request = { NetworkHandler.getService().corGetCompanies(start, end).execute() },
                onResult = {
                    it.toMutableList().let {
                        viewState.setListToAdapter(it)
                        allCompanies.addAll(it)
                        currentCompanies.addAll(it)
                    }
                }
        )
    }

    fun onStart() {
        corMethod<List<ConstDict>>(
                request = { NetworkHandler.getService().onStart().execute() },
                onResult = {
                    Storage.constDictList = it.toMutableList()
                }
        )
    }

    fun clickOnPosition(position: Int) {
        currentCompanies[position].id.let { viewState.startOneCompanyActivity(it) }
    }

    fun searchedList(searchString: String) {
        if (searchString.isEmpty()) {
            currentCompanies.clear()
            allCompanies.let { currentCompanies.addAll(it) }
            viewState.setListToAdapter(currentCompanies)
            return
        } else {
            val words = searchString.split(" ")

            currentCompanies.clear()
            allCompanies.let { currentCompanies.addAll(it) }

            for (word in words) {
                val tasks = ArrayList<Company>()
                for (task in currentCompanies) {
                    val sb = StringBuilder()
                    if (task.title.isNotEmpty()) {
                        sb.append(task.title.toLowerCase())
                    }
                    if (task.address.isNotEmpty()) {
                        sb.append(" ")
                        sb.append(task.address.toLowerCase())
                    }
                    if (task.title.isNotEmpty() && task.address.isNotEmpty()) {
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