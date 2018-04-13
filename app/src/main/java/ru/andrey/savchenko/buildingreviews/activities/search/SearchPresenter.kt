package ru.andrey.savchenko.buildingreviews.activities.search

import com.arellomobile.mvp.InjectViewState
import ru.andrey.savchenko.buildingreviews.base.BasePresenter

/**
 * Created by savchenko on 10.04.18.
 */
@InjectViewState
class SearchPresenter :BasePresenter<SearchView>() {
    val interActor = SearchInterActor()

    fun getCompanyList(){
        interActor.getCompanyList()
                .compose(DialogTransformer())
                .subscribe({list -> viewState.setListToAdapter(list)},
                        {t -> t.printStackTrace()})
    }
}