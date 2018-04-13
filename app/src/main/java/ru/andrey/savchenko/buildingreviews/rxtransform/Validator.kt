package ru.andrey.savchenko.buildingreviews.rxtransform

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.tander.pharmacy.network.Response

/**
 * Created by savchenko on 04.04.18.
 */
class Validator<T> : SingleTransformer<Response<T>, T> {
    override fun apply(upstream: Single<Response<T>>): SingleSource<T> {
        return upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { res ->
                    if (res is Response<*>) {
                        if (res.error != null) {
                            return@flatMap Single.error<T>(Throwable(res.error.message))
                        }
                    }
                    return@flatMap Single.just(res.data)
                }
    }
}