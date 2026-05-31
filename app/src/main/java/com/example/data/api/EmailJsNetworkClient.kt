package com.example.data.api

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object EmailJsNetworkClient {

    private val okHttpClient = OkHttpClient.Builder().build()

    private val moshi = Moshi.Builder()
        .build()

    val emailJsApi: EmailJsApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.emailjs.com/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(EmailJsApi::class.java)
    }
}
