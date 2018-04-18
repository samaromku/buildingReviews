package ru.andrey.savchenko.buildingreviews.storage

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Response
import ru.andrey.savchenko.buildingreviews.base.BaseView

/**
 * Created by savchenko on 18.04.18.
 */
class Coroutiner<T>(private val viewState: BaseView) {
    fun corMethod(beforeRequest: () -> Unit = { viewState.showDialog() },
                  afterRequest: () -> Unit = { viewState.hideDialog() },
                  request: () -> Response<T>,
                  onResult: (result: T) -> Unit,
                  errorShow: (t: Throwable) -> Unit = { t ->
                      t.printStackTrace()
                      viewState.showError(t.message.toString())
                  }) {
        launch(UI) {
            beforeRequest.invoke()
            var result: Response<T>? = null
            try {
                result = async(CommonPool) {
                    request.invoke()
                }.await()
            } catch (ex: Throwable) {
                errorShow.invoke(ex)
            }
            afterRequest.invoke()
            result?.body()?.let {
                onResult.invoke(it)
            }
        }
    }
}