package ir.danialchoopan.danialkala.data.api.volleyRequestes.product.search

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.data.model.requests.searchProduct.SearchProductDataModel
import java.lang.Exception

class SearchProductVolleyRequest(private val m_context: Context) {
    fun query(
        query: String,
        resultRequest: (success: Boolean, productSearch: SearchProductDataModel) -> Unit
    ) {
        val str_request = object : StringRequest(Request.Method.POST,
            EndPoints.searchProduct, { strResponse ->
                try {
                    val gsonSearchProduct = Gson().fromJson(
                        strResponse,
                        SearchProductDataModel::class.java
                    )
                    resultRequest(true, gsonSearchProduct)
                } catch (e: Exception) {
                    resultRequest(false, SearchProductDataModel())
                }
            },
            //error
            {
                it.printStackTrace()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val requestBody = HashMap<String, String>()
                requestBody["queryProduct"] = query
                return requestBody
            }
        }//end volley request
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }
}