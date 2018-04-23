package ru.andrey.savchenko.buildingreviews.base

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import retrofit2.Response
import ru.andrey.savchenko.buildingreviews.entities.network.ApiResponse
import java.util.concurrent.TimeUnit

/**
 * Created by savchenko on 22.04.18.
 */
interface BasePresenterNoMvp {
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

    fun showDialog()
    fun hideDialog()
    fun showError(error:String)
}