package ru.andrey.savchenko.buildingreviews.base

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import retrofit2.Response
import ru.andrey.savchenko.buildingreviews.activities.onecompany.INFO
import ru.andrey.savchenko.buildingreviews.activities.onecompany.OneCompanyPresenter
import ru.andrey.savchenko.buildingreviews.entities.network.ApiResponse
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorRepeat
import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse
import ru.andrey.savchenko.buildingreviews.entities.network.RestThrowable
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by savchenko on 29.05.18.
 */
interface BasePresenterProgress {
    var routerPresenter: OneCompanyPresenter?

    fun <T> corMethod(beforeRequest: () -> Unit = { showDialog() },
                      afterRequest: (key:String) -> Unit = {key ->  hideDialog(key) },
                      request: () -> Response<ApiResponse<T>>,
                      onResult: (result: T) -> Unit,
                      errorShow: (error: ErrorResponse) -> Unit = { t ->
                          showError(t, {
                              this.corMethod(onResult = onResult,
                                      request = request,
                                      fragmentKey = fragmentKey)
                          })
                      }, fragmentKey:String): Job {
        return launch(UI) {
            beforeRequest()
            var result: Response<ApiResponse<T>>? = null
            try {
                result = async(CommonPool) { request() }.await()

                afterRequest(fragmentKey)

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
            }catch (ex: RestThrowable){
                ex.printStackTrace()
                errorShow(ex.errorResponse)
            } catch (ex: Throwable) {
                ex.printStackTrace()
                errorShow(ErrorResponse(ex.message.toString(), 500))
            }
        }
    }

    fun showDialog(){
        routerPresenter?.showDialog()
    }
    fun hideDialog(key:String){
        routerPresenter?.hideDialog(key)
    }
    fun showError(error: ErrorResponse, repeat: () -> Unit){
        routerPresenter?.showError(ErrorRepeat(error, repeat))
    }
}