package ir.danialchoopan.danialkala.data.api.volleyRequestes

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.data.model.requests.productReview.ProductReview
import java.lang.Exception

class ShowProductReviewVolleyRequest(private val m_context: Context) {
    fun sendRequest(
        productId: String,
        requestResult: (success: Boolean, productReview: ProductReview) -> Unit
    ) {
        val strRequest =
            StringRequest(Request.Method.GET,
                "${EndPoints.baseUrl}product/${productId}/review",
                { strResult ->
//                    try {
                        val gsonProductReview =
                            Gson().fromJson(strResult, ProductReview::class.java)
                        requestResult(true, gsonProductReview)
//                    } catch (e: Exception) {
//                        requestResult(
//                            false,
//                            ProductReview(
//                                "",
//                                0,
//                                "",
//                                "",
//                                "",
//                            )
//                        )
//                    }
                },
                {
                    it.printStackTrace()
                })
        VolleySingleTon.getInstance(m_context).addToRequestQueue(strRequest)
    }
}
