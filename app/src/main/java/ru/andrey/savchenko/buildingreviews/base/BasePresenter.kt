package ru.andrey.savchenko.buildingreviews.base

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Response
import ru.andrey.savchenko.buildingreviews.entities.network.ApiResponse

/**
 * Created by savchenko on 10.04.18.
 */
open class BasePresenter<T : BaseView> : MvpPresenter<T>() {

    fun showProgress() {
        viewState.showProgress()
    }

    fun hideProgress() {
        viewState.hideProgress()
    }


    fun showDialog() {
        viewState.showDialog()
    }

    fun hideDialog() {
        viewState.hideDialog()
    }

    fun showError(error:String){
        viewState.showError(error)
    }

    fun <T> corMethod(beforeRequest: () -> Unit = { showDialog() },
                      afterRequest: () -> Unit = { hideDialog() },
                      request: () -> Response<ApiResponse<T>>,
                      onResult: (result: T) -> Unit,
                      errorShow: (error: String) -> Unit = { t -> showError(t) }): Job {
        return launch(UI) {
            beforeRequest()
            var result: Response<ApiResponse<T>>? = null
            try {
                result = async(CommonPool) { request() }.await()
            } catch (ex: Throwable) {
                ex.printStackTrace()
                errorShow(ex.message.toString())
            }
            afterRequest()

            if (result != null) {
                if (result.body() != null) {
                    val body = result.body()
                    if (body is ApiResponse<*>) {
                        if (body.error != null) {
                            errorShow("Код: ${body.error.code}\n" +
                                    "Ошибка: ${body.error.message} ")
                            return@launch
                        } else {
                            body.data?.let { onResult(it) }
                        }
                    }
                } else {
                    errorShow("responseBody = null")
                }
            } else {
                errorShow("result = null")
            }
        }
    }
}