package ru.andrey.savchenko.buildingreviews.fragments.choose_region

import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import ru.andrey.savchenko.App
import ru.andrey.savchenko.buildingreviews.base.BasePresenterNoMvp
import ru.andrey.savchenko.buildingreviews.db.Repository
import ru.andrey.savchenko.buildingreviews.entities.Region
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.storage.Const.Companion.NOTHING_CHOSEN

/**
 * Created by savchenko on 24.04.18.
 */
class ChooseRegionPresenter(val view: ChooseRegionView) : BasePresenterNoMvp {
    private var allRegions: MutableList<Region>? = null

    fun getRegions() {
        launch(UI) {
            val dbRegions = Repository().getAllRegions()

            if (dbRegions.isNotEmpty()) {
                allRegions = dbRegions
                allRegions?.let { view.setListToAdapter(it) }
            } else {
                corMethod(request = { NetworkHandler.getService().getRegions().execute() },
                        onResult = {
                            val regions = it.map { Region(value = it) }.toMutableList()
                            allRegions = regions
                            allRegions?.let { regions ->
                                view.setListToAdapter(regions)
                                Repository().dbQueryWrapper {
                                    it.regionDao().insertOrUpdateAll(regions)
                                }
                            }
                        })
            }
        }
    }

    fun clickOnRegion(position: Int) {
        val region = allRegions?.get(position)
        region?.let {
            it.selected = !it.selected
            Repository().dbQueryWrapper({
                it.regionDao().insertOrUpdate(region)
            })
        }
        view.updateAdapter()
    }

    fun setAllSelected() {
        val selected = allRegions?.filter { it.selected }
        if (allRegions != null) {
            selected?.let {
                if (selected.isEmpty() || selected.size < allRegions?.size!!) {
                    makeRegionsSelected(true)
                } else {
                    makeRegionsSelected(false)
                }
            }
        }
        view.updateAdapter()
    }

    private fun makeRegionsSelected(selected: Boolean) {
        allRegions?.let { regions ->
            for (region in allRegions!!) {
                region.selected = selected
            }
            Repository().dbQueryWrapper {
                it.regionDao().insertOrUpdateAll(regions)
            }
        }
    }

    fun getSelectedRegions() {
        val selected = allRegions?.filter { it.selected }
        selected?.let {
            if (it.isEmpty()) {
                view.showToast(NOTHING_CHOSEN)
            } else {
                selected.toMutableList().let { view.getSelectedRegions(it) }
            }
        }
    }

    override fun showDialog() {

    }

    override fun hideDialog() {

    }

    override fun showError(error: ErrorResponse, repeat: () -> Unit) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
