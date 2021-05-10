package ir.danialchoopan.danialkala.data.api.home

import retrofit2.http.GET

interface ShowCategoryRequest {
    @GET("categoryproduct/7")
    suspend fun showCategories()
}