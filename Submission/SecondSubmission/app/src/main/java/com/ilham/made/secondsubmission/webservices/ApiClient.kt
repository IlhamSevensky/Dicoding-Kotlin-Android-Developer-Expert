package com.ilham.made.secondsubmission.webservices

import com.ilham.made.secondsubmission.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private var option = OkHttpClient.Builder().apply {
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
    }.build()

    private val retrofit = Retrofit.Builder().apply {
        client(option)
        baseUrl(BuildConfig.BASE_URL_TSDB)
        addConverterFactory(GsonConverterFactory.create())
    }.build()

    val instance: ApiService = retrofit.create(ApiService::class.java)
}