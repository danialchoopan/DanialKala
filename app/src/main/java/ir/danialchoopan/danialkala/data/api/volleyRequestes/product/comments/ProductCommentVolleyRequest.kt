package ir.danialchoopan.danialkala.data.api.volleyRequestes.product.comments

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.data.model.requests.productComment.ProductComment
import org.json.JSONObject

class ProductCommentVolleyRequest(private val m_context: Context) {
    private val userSharePreferences = UserSharePreferences(m_context)


    fun productComments(
        productId: String,
        requestResult: (success: Boolean, productComment: ProductComment) -> Unit
    ) {
        val str_request =
            object : StringRequest(Request.Method.GET, EndPoints.productComment + productId,
                { strResponse ->
                    try {
                        val productComment =
                            Gson().fromJson<ProductComment>(strResponse, ProductComment::class.java)
                        requestResult(true, productComment)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        requestResult(false, ProductComment())
                    }
                },
                //error
                { error ->
                    error.printStackTrace()
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val token_access = userSharePreferences.getToken()
                    val requestHeaders = HashMap<String, String>()
                    requestHeaders["Authorization"] = "Bearer $token_access";
                    return requestHeaders

                }
            }//end str request
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }

    fun addProductComments(
        productId: String,
        comment: String,
        requestResult: (success: Boolean) -> Unit
    ) {
        val str_request =
            object : StringRequest(Request.Method.POST, EndPoints.productComment,
                { strResponse ->
                    try {
                        val jsonResponse = JSONObject(strResponse)
                        requestResult(jsonResponse.getBoolean("success"))
                    } catch (e: Exception) {
                        e.printStackTrace()
                        requestResult(false)
                    }
                },
                //error
                { error ->
                    error.printStackTrace()
                }
            ) {

                override fun getParams(): MutableMap<String, String> {
                    val requestParams = HashMap<String, String>()
                    requestParams["product_id"] = productId
                    requestParams["comment"] = comment
                    return requestParams
                }

                override fun getHeaders(): MutableMap<String, String> {
                    val token_access = userSharePreferences.getToken()
                    val requestHeaders = HashMap<String, String>()
                    requestHeaders["Authorization"] = "Bearer $token_access";
                    return requestHeaders

                }
            }//end str request
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }

    fun deleteComment(
        commentId: String,
        requestResult: (success: Boolean) -> Unit
    ) {

        val str_request =
            object : StringRequest(Request.Method.DELETE, EndPoints.productComment + commentId,
                { strResponse ->
                    try {
                        val jsonResult = JSONObject(strResponse)
                        requestResult(jsonResult.getBoolean("success"))
                    } catch (e: Exception) {
                        e.printStackTrace()
                        requestResult(false)
                    }
                },
                //error
                { error ->
                    error.printStackTrace()
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val token_access = userSharePreferences.getToken()
                    val requestHeaders = HashMap<String, String>()
                    requestHeaders["Authorization"] = "Bearer $token_access";
                    return requestHeaders

                }
            }//end str request
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }

}