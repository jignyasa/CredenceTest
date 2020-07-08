package com.ma.credencetest.remote

import com.google.gson.GsonBuilder
import com.ma.credencetest.BuildConfig
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.net.CookieManager
import java.util.concurrent.TimeUnit

class Retrofit {
    companion object{
        private const val NETWORK_READ_TIME_OUT = 60L
        private const val NETWORK_CONNECT_TIME_OUT = 60L

        private fun getHttpClient(): OkHttpClient {
            val httpClient = OkHttpClient.Builder()
            httpClient.readTimeout(NETWORK_READ_TIME_OUT, TimeUnit.SECONDS)
            httpClient.connectTimeout(NETWORK_CONNECT_TIME_OUT, TimeUnit.SECONDS)
            httpClient.cookieJar(JavaNetCookieJar(CookieManager()))

            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(logging)
            }

            return httpClient.build()
        }
        fun getRetrofit(): ApiService
        {
            return Retrofit.Builder()
                .baseUrl("http://credencetech.in/mobiledevelopertechnicaltest/")
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getHttpClient())
                .build().create(ApiService::class.java)
        }
    }
}