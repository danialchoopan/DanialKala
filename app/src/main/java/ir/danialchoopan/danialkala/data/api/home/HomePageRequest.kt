package ir.danialchoopan.danialkala.data.api.home

import ir.danialchoopan.danialkala.data.model.requests.home.HomeRequestModel
import retrofit2.http.GET

interface HomePageRequest {

    @GET("home")
    suspend fun getHome():HomeRequestModel
}