package ir.danialchoopan.danialkala.data.api.repository

import ir.danialchoopan.danialkala.data.api.RetrofitInstance
import ir.danialchoopan.danialkala.data.model.requests.category.Category_Base

class CategoryRequestRepository {
    suspend fun categoryRequest(): List<Category_Base> {
        return RetrofitInstance.categoryRequest.getCategories()
    }
}