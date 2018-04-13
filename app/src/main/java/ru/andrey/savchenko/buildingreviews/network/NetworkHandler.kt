package ru.andrey.savchenko.buildingreviews.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.Gson



/**
 * Created by savchenko on 11.04.18.
 */
object NetworkHandler {
    val BASE_HOST_INNER = "http://10.0.2.2:8666"//mine computer emulator

    fun getService(): BuildingService {
        val client: OkHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

        val gson = GsonBuilder()
                .setLenient()
                .create()
        //real server
        return Retrofit.Builder()
                .baseUrl(BASE_HOST_INNER)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(BuildingService::class.java)
    }

}