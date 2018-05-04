package ru.andrey.savchenko.buildingreviews.activities.search

import android.location.Location
import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.entities.ConstDict
import ru.andrey.savchenko.buildingreviews.entities.network.ApiResponse
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.storage.Const.Companion.MAP_KEY
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
        val itemCountStorage = Storage.getValueConst("itemCount")
        if(itemCountStorage!=null){
            if (start >= itemCountStorage.toInt()) {
                return
            }
            if (end >= itemCountStorage.toInt()) {
                end = itemCountStorage.toInt()
            }
            println("start $start end $end")
            corMethod<List<Company>>(
                    beforeRequest = { showProgress() },
                    afterRequest = { hideProgress() },
                    request = { NetworkHandler.getService().corGetCompanies(start, end).execute() },
                    onResult = {
                        it.toMutableList().let {
                            allCompanies.addAll(it)
                            currentCompanies.addAll(it)
                            viewState.setListToAdapter(currentCompanies)
                        }
                    }
            )
        }
    }

    fun getAddress(location: Location){
        val locationString = "${location.latitude},${location.longitude}"
        launch(CommonPool) {
            try {
                val result = async(CommonPool) {
                    NetworkHandler.getGeoCodeService()
                            .geoCode(locationString, MAP_KEY).execute()
                }.await()
                println("gello result ${result.body()}")
            }catch (ex:Throwable){
                ex.printStackTrace()
            }
        }
    }

    fun onStart() {
        corMethod<List<ConstDict>>(
                request = { NetworkHandler.getService().onStart().execute() },
                onResult = {
                    Storage.constDictList = it.toMutableList()
                    corCompanyList(0)
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