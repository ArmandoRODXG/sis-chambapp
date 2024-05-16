package com.project.chambaapp.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    inline fun <reified T> createService(baseUrl: String): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(T::class.java)
    }
}

//object RetrofitClient{
////    private const val BASE_URL = "https://is-chambapp-5bf6977200ac.herokuapp.com/contratistas/"
////    private const val BASE_URL = "http://192.168.1.3:5000/contratistas/"
//
//    private var baseUrl: String = ""
//
//    fun setBaseUrl(url: String) {
//        baseUrl = url
//    }
//
//    val instance: ContratistasService by lazy {
//        val retrofit = Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        retrofit.create(ContratistasService::class.java)
//    }
//}