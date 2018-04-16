package ru.andrey.savchenko.buildingreviews.entities.network


/**
 * Created by savchenko on 01.03.18.
 */
class ApiResponse<out T> (val data:T?,
                          val error: ErrorResponse? = null)