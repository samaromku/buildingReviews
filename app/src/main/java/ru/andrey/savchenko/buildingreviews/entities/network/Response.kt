package ru.tander.pharmacy.network

import ru.andrey.savchenko.buildingreviews.entities.ErrorResponse

/**
 * Created by savchenko on 01.03.18.
 */
class Response<out T> (val data:T,
                       val error: ErrorResponse? = null)