package ru.andrey.savchenko.buildingreviews.network

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import ru.andrey.savchenko.buildingreviews.entities.Company

/**
 * Created by savchenko on 11.04.18.
 */
interface BuildingService {
    @GET("/mobile/getCompanies")
    fun getCompanies(): Single<List<Company>>
}

