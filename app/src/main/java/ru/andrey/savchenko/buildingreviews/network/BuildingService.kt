package ru.andrey.savchenko.buildingreviews.network

import entities.Building
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.andrey.savchenko.buildingreviews.entities.Company
import ru.andrey.savchenko.buildingreviews.entities.Review

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

    @GET("/mobile/getReviewsByCompanyId")
    fun getReviewsByCompanyId(@Query("companyId")companyId:Int):Call<List<Review>>

    @GET("/mobile/getBuildingsByCompanyId")
    fun getBuildingsByCompanyId(@Query("companyId")companyId:Int):Call<List<Building>>

}

