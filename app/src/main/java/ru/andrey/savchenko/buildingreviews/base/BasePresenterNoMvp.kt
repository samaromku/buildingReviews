package ru.andrey.savchenko.buildingreviews.base

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
import retrofit2.Response
import ru.andrey.savchenko.buildingreviews.entities.network.ApiResponse
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.andrey.savchenko.buildingreviews.entities.network.RestThrowable
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
                      errorShow: (error: ErrorResponse) -> Unit = { t ->
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
                            throw RestThrowable(ErrorResponse(
                                    message = body.error.message,
                                    code = body.error.code))
                        } else {
                            body.data?.let { onResult(it) }
                        }
                    }
                } else {
                    throw Throwable("Вернулся пустой ответ с сервера")
                }
            } catch (ex: SocketTimeoutException) {
                ex.printStackTrace()
                errorShow(ErrorResponse("Превышено время ожидания ответа с сервера",
                        408))
            } catch (ex: ConnectException) {
                ex.printStackTrace()
                errorShow(ErrorResponse("Не удалось подключиться к серверу \nПроверьте свое подключение к интернету",
                        503))
            }catch (ex:RestThrowable){
                ex.printStackTrace()
                errorShow(ex.errorResponse)
            } catch (ex: Throwable) {
                ex.printStackTrace()
                errorShow(ErrorResponse(ex.message.toString(), 500))
            }
            afterRequest()
        }
    }

    fun bdExecute(exec:() -> Any){

    }

    fun showDialog()
    fun hideDialog()
    fun showError(error: ErrorResponse, repeat: () -> Unit)
}