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
    inner class ProgressBarTransformer<T> : SingleTransformer<T, T> {
        override fun apply(upstream: Single<T>): Single<T>? {
            return upstream
                    .doOnSubscribe { showProgress() }
                    .doFinally({ hideProgress() })
        }
    }

    fun showProgress() {
        viewState.showProgress()
    }

    fun hideProgress() {
        viewState.hideProgress()
    }

    inner class DialogTransformer<T> : SingleTransformer<T, T> {
        override fun apply(upstream: Single<T>): SingleSource<T> {
            return upstream
                    .doOnSubscribe { showDialog() }
                    .doFinally({ hideDialog() })
        }
    }

    fun showDialog() {
        viewState.showDialog()
    }

    fun hideDialog() {
        viewState.hideDialog()
    }

    fun <T> corMethod(beforeRequest: () -> Unit = { viewState.showDialog() },
                      afterRequest: () -> Unit = { viewState.hideDialog() },
                      request: () -> Response<ApiResponse<T>>,
                      onResult: (result: T) -> Unit,
                      errorShow: (error: String) -> Unit = { t -> viewState.showError(t) }): Job {
        return launch(UI) {
            beforeRequest()
            var result: Response<ApiResponse<T>>? = null
            try {
                result = async(CommonPool) {
                    request()
                }.await()
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