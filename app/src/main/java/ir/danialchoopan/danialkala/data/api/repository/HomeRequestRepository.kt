package ir.danialchoopan.danialkala.data.api.repository

import ir.danialchoopan.danialkala.data.api.RetrofitInstance
import ir.danialchoopan.danialkala.data.model.requests.home.HomeRequestModel

class HomeRequestRepository {
    suspend fun homeRequest(): HomeRequestModel {
        return RetrofitInstance.homeRequest.getHome()
    }
}