package ru.andrey.savchenko.buildingreviews.fragments.choose_region

import ru.andrey.savchenko.buildingreviews.base.BasePresenterNoMvp
import ru.andrey.savchenko.buildingreviews.db.BaseDao
import ru.andrey.savchenko.buildingreviews.db.Dao
import ru.andrey.savchenko.buildingreviews.entities.Region
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.andrey.savchenko.buildingreviews.network.NetworkHandler
import ru.andrey.savchenko.buildingreviews.storage.Const.Companion.NOTHING_CHOSEN

/**
 * Created by savchenko on 24.04.18.
 */
class ChooseRegionPresenter(val view: ChooseRegionView) : BasePresenterNoMvp {
    var allRegions: MutableList<Region>? = null

    fun getRegions() {

        val dbRegions = BaseDao(Region::class.java).getAll()
        if (dbRegions.isNotEmpty()) {
            allRegions = dbRegions.toMutableList()
            allRegions?.let { view.setListToAdapter(it) }
        } else {
            corMethod(request = { NetworkHandler.getService().getRegions().execute() },
                    onResult = {
                        val regions = it.map { Region(value = it) }.toMutableList()
                        allRegions = regions
                        allRegions?.let {
                            view.setListToAdapter(it)
                            BaseDao(Region::class.java).addList(it)
                        }
                    })
        }
    }

    fun clickOnRegion(position: Int) {
        val region = allRegions?.get(position)
        region?.let { Dao().setRegionSelected(it) }
        region?.selected?.let {
            region.selected = !it
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
        allRegions?.let {
            for (region in allRegions!!) {
                region.selected = selected
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
