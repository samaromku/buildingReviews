package ru.andrey.savchenko.buildingreviews.network

import entities.Building
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.andrey.savchenko.buildingreviews.entities.*
import ru.andrey.savchenko.buildingreviews.entities.network.ApiResponse

/**
 * Created by savchenko on 11.04.18.
 */
interface BuildingService {
    @POST("/mobile/getCompanies")
    fun getGetCompaniesByCity(@Body companyFilter: CompanyFilter):Call<ApiResponse<CompaniesItemCount>>

    @GET("/mobile/getCompany")
    fun getCompany(@Query("id")id:Int):Call<ApiResponse<Company>>

    @GET("/mobile/reviews")
    fun getReviewsByCompanyId(@Query("companyId")companyId:Int):Call<ApiResponse<List<Review>>>

    @POST("/mobile/reviews")
    fun sendReview(@Body review: Review):Call<ApiResponse<Boolean>>

    @GET("/mobile/getBuildingsByCompanyId")
    fun getBuildingsByCompanyId(@Query("companyId")companyId:Int):Call<ApiResponse<List<Building>>>

    @GET("/mobile/auth")
    fun auth(@Query("login")login:String,
             @Query("password")password:String):Call<ApiResponse<User>>

    @POST("/mobile/auth")
    fun register(@Body user: User):Call<ApiResponse<User>>

    @POST("/mobile/like")
    fun addLike(@Body like: Like):Call<ApiResponse<Review>>

    @GET("/mobile/onStart")
    fun onStart():Call<ApiResponse<List<ConstDict>>>

    @GET("/mobile/getRegions")
    fun getRegions():Call<ApiResponse<MutableList<String>>>
}

