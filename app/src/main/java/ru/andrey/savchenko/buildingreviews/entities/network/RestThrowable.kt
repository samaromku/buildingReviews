package ru.andrey.savchenko.buildingreviews.entities.network

import ru.andrey.savchenko.buildingreviews.entities.network.ErrorResponse

/**
 * Created by savchenko on 28.04.18.
 */
data class RestThrowable(val errorResponse: ErrorResponse):Throwable()