package ir.danialchoopan.danialkala.data.api.volleyRequestes.product

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.data.model.requests.singleProduct.Productphoto
import ir.danialchoopan.danialkala.data.model.requests.singleProduct.SingleProduct
import ir.danialchoopan.danialkala.data.model.requests.singleProduct.Store

class ShowSingleProductVolleyRequest(private val m_context: Context) {
    fun getProductById(
        productId: String,
        requestResult: (success: Boolean, product: SingleProduct) -> Unit
    ) {
        val str_request = StringRequest(Request.Method.GET, EndPoints.showProduct +"/"+ productId,
            { strResponse ->
                try {
                    val productModel =
                        Gson().fromJson<SingleProduct>(strResponse, SingleProduct::class.java)
                    requestResult(true, productModel)
                } catch (e: Exception) {
                    requestResult(
                        false,
                        SingleProduct(
                            "",
                            0,
                            "",
                            "",
                            "",
                            "",
                            0,
                            "",
                            "",
                            emptyList(),
                            "",
                            emptyList(),
                            0,
                            "",
                            "",
                        )//end single product data model
                    )//end result
                }//end catch
            },
            //error
            {
                it.printStackTrace()
            }
        )//end request
        VolleySingleTon(m_context).addToRequestQueue(str_request)
    }//end fun show product
}