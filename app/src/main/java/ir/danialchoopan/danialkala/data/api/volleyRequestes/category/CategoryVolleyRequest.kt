package ir.danialchoopan.danialkala.data.api.volleyRequestes.category

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.data.model.requests.subCategory.SubCategory
import ir.danialchoopan.danialkala.data.model.requests.subCategory.SubCategoryItem

class CategoryVolleyRequest(private val m_context: Context) {
    fun getSubCategory(
        categoryId: String,
        requestResult: (success: Boolean, subCategory: List<SubCategoryItem>) -> Unit
    ) {
        val str_request = StringRequest(Request.Method.GET, EndPoints.subCategory +"/"+ categoryId,
            { str_response ->
                try {
                    val subCategory =
                        Gson().fromJson<List<SubCategoryItem>>(str_response, SubCategory::class.java)
                    requestResult(true, subCategory)
                } catch (e: Exception) {
                    requestResult(false, SubCategory())
                }
            },
            //error
            {
                it.printStackTrace()
            })
        VolleySingleTon(m_context).addToRequestQueue(str_request)
    }
}