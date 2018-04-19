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

    protected fun checkResponse(response: ApiResponse<Any>, success: () -> Unit) {
        if (response.error != null) {
            viewState.showError(response.error.message)
            return
        } else {
            success.invoke()
        }
    }

    fun<T> corMethod(beforeRequest: () -> Unit = { viewState.showDialog() },
                     afterRequest: () -> Unit = { viewState.hideDialog() },
                     request: () -> Response<T>,
                     onResult: (result: T) -> Unit,
                     errorShow: (error:String) -> Unit = { t -> viewState.showError(t) }):Job {
        return launch(UI) {
            beforeRequest.invoke()
            var result: Response<T>? = null
            try {
                result = async(CommonPool) {
                    request.invoke()
                }.await()
            } catch (ex: Throwable) {
                ex.printStackTrace()
                errorShow.invoke(ex.message.toString())
            }
            afterRequest.invoke()

            if(result!=null){
                if(result.body()!=null){
                    val body = result.body()
                    if (body is ApiResponse<*>) {
                        if (body.error != null) {
                            errorShow.invoke("Код: ${body.error.code}\n" +
                                    "Ошибка: ${body.error.message} ")
                            return@launch
                        } else {
                            onResult.invoke(body)
                        }
                    }
                }else {
                    errorShow.invoke("responseBody = null")
                }
            }else {
                errorShow.invoke("result = null")
            }
        }
    }
}