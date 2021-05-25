package ir.danialchoopan.danialkala.data.api.volleyRequestes.product.more

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.data.model.requests.moreProduct.MoreProductDataModel

class MoreProductVolleyRequest(private val m_context: Context) {
    fun getAllProducts(
        resultRequest: (success: Boolean, moreAllProduct: MoreProductDataModel) -> Unit
    ) {
        val str_request = StringRequest(Request.Method.GET, EndPoints.allProduct,
            { str_response ->
                try {
                    val gsonProducts =
                        Gson().fromJson(str_response, MoreProductDataModel::class.java)
                    resultRequest(true, gsonProducts)
                } catch (e: Exception) {
                    resultRequest(false, MoreProductDataModel())
                }
            },
            //error
            {
                it.printStackTrace()
            })//end requset
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }
}