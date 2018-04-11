package ru.andrey.savchenko.buildingreviews.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * Created by savchenko on 11.04.18.
 */
object NetworkHandler {
    fun getService():BuildingService{
        val client: OkHttpClient = OkHttpClient
                .Builder()
                .addInterceptor(HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

        //real server
        return Retrofit.Builder()
                .baseUrl("https://novostroi-ki.ru/")
                .client(client)
                .addConverterFactory(JspoonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(BuildingService::class.java)
    }

}