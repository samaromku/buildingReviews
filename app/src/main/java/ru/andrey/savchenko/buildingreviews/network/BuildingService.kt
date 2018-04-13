package ru.andrey.savchenko.buildingreviews.network

import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.andrey.savchenko.buildingreviews.entities.Company

/**
 * Created by savchenko on 11.04.18.
 */
interface BuildingService {
    @GET("/mobile/getCompanies")
    fun getCompanies(): Single<List<Company>>

    @GET("/mobile/getCompanies")
    fun corGetCompanies():Call<List<Company>>

    @GET("/mobile/getCompany")
    fun getCompany(@Query("id")id:Int):Call<Company>


}

