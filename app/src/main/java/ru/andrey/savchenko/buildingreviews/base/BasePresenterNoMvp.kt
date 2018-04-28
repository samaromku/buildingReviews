package ru.andrey.savchenko.buildingreviews.base

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import retrofit2.Response
import ru.andrey.savchenko.buildingreviews.entities.network.ApiResponse
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

/**
 * Created by savchenko on 22.04.18.
 */
interface BasePresenterNoMvp {
    fun <T> corMethod(beforeRequest: () -> Unit = { showDialog() },
                      afterRequest: () -> Unit = { hideDialog() },
                      request: () -> Response<ApiResponse<T>>,
                      onResult: (result: T) -> Unit,
                      errorShow: (error: String) -> Unit = { t ->
                          showError(t, {
                              //todo  repeat
                              this.corMethod(onResult = onResult,
                                      request = request)
                          })
                      }): Job {
        return launch(UI) {
            beforeRequest()
            var result: Response<ApiResponse<T>>? = null
            try {
                result = async(CommonPool) { request() }.await()
                if (result.body() != null) {
                    val body = result.body()
                    if (body is ApiResponse<*>) {
                        if (body.error != null) {
                            throw Throwable("Код: ${body.error.code}\n" +
                                    "Ошибка: ${body.error.message} ")
                        } else {
                            body.data?.let { onResult(it) }
                        }
                    }
                } else {
                    throw Throwable("Вернулся пустой ответ с сервера")
                }
            } catch (ex: SocketTimeoutException) {
                ex.printStackTrace()
                errorShow("Превышено время ожидания ответа с сервера")
            }catch (ex: ConnectException){
                ex.printStackTrace()
                errorShow("Не удалось подключиться к серверу \nПроверьте свое подключение к интернету")
            } catch (ex: Throwable) {
                ex.printStackTrace()
                errorShow(ex.message.toString())
            }
            afterRequest()
        }
    }

    fun showDialog()
    fun hideDialog()
    fun showError(error: String, repeat: () -> Unit)
}