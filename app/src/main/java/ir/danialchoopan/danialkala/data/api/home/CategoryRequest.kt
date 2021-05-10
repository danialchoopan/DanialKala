package ir.danialchoopan.danialkala.data.api.home

import ir.danialchoopan.danialkala.data.model.requests.category.Category_Base
import retrofit2.http.GET

interface CategoryRequest {

    @GET("category")
    suspend fun getCategories(): List<Category_Base>
}