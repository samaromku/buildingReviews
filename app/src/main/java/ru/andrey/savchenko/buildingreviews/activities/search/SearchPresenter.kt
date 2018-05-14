package ru.andrey.savchenko.buildingreviews.activities.search

import android.location.Location
import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import ru.andrey.savchenko.App
import ru.andrey.savchenko.buildingreviews.base.BasePresenter
import ru.andrey.savchenko.buildingreviews.entities.CompaniesItemCount
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.entities.CompanyFilter
import ru.andrey.savchenko.buildingreviews.entities.Region
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


    fun getCompanyList(itemCount: Int) {
        val start: Int = itemCount
        var end: Int = start + 10
        val itemCountStorage = Storage.itemCount
        if (itemCountStorage != null) {
            if (start >= itemCountStorage.toInt()) {
                return
            }
            if (end >= itemCountStorage.toInt()) {
                end = itemCountStorage.toInt()
            }
            println("start $start end $end")
            corMethod<CompaniesItemCount>(
                    beforeRequest = { showProgress() },
                    afterRequest = { hideProgress() },
                    request = {
                        NetworkHandler.getService().getGetCompaniesByCity(
                                CompanyFilter(
                                        start = start,
                                        end = end,
                                        regions = getRegionsList())).execute()
                    },
                    onResult = {
                        it.companyList.toMutableList().let {
                            allCompanies.addAll(it)
                            currentCompanies.addAll(it)
                            viewState.setListToAdapter(currentCompanies)
                        }
                        println("itemcount here ${it.itemCount}")
                        if(it.itemCount!=0) {
                            Storage.itemCount = it.itemCount
                        }else {
                            println("itemcount here failed $it")
                        }
                    }
            )
        }
    }

    private fun getRegionsList():MutableList<Region>{
        val regionList: MutableList<Region>
        val dbRegions = App.database.regionDao().getAll()
        regionList = if(dbRegions.isEmpty()){
            mutableListOf<Region>(Region("Москва", true))
        }else {
            dbRegions.filter { it.selected }.toMutableList()
        }
        return regionList
    }

    fun uploadNewList(){
        currentCompanies.clear()
        allCompanies.clear()
        getCompanyList(0)
        viewState.updateAdapter()
    }

    fun getAddress(location: Location) {
        val locationString = "${location.latitude},${location.longitude}"
        launch(CommonPool) {
            try {
                val result = async(CommonPool) {
                    NetworkHandler.getGeoCodeService()
                            .geoCode(locationString, MAP_KEY, "ru").execute()
                }.await()
                println("gello result ${result.body()}")
                val results = result.body()?.results
                if (results != null) {
                    for (result in results) {
                        val types = result.types
                        if (types != null) {
                            for (type in types) {
                                if (type == "locality") {
                                    //get current position and set it to current regionList
//                                    regionList = mutableListOf(Region(result.addressComponents?.get(0).toString()))
                                }
                            }
                        }
                    }
                }
            } catch (ex: Throwable) {
                ex.printStackTrace()
            }
        }
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