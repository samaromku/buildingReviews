package ru.andrey.savchenko.buildingreviews.network

import io.reactivex.Single
import retrofit2.http.GET
import ru.andrey.savchenko.buildingreviews.entities.response.Page

/**
 * Created by savchenko on 11.04.18.
 */
interface BuildingService {
    @GET("/krasnodar/companies/")
    fun getPosts():Single<String>
}