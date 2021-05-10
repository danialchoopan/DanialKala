package ir.danialchoopan.danialkala.data.api

import ir.danialchoopan.danialkala.data.api.home.CategoryRequest
import ir.danialchoopan.danialkala.data.api.home.HomePageRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(EndPoints.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val homeRequest by lazy {
        retrofit.create(HomePageRequest::class.java)
    }

    val categoryRequest by lazy {
        retrofit.create(CategoryRequest::class.java)
    }
}