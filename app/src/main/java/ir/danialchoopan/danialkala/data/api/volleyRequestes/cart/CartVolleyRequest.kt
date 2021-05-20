package ir.danialchoopan.danialkala.data.api.volleyRequestes.cart

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.data.model.requests.userCart.CartDataModel
import ir.danialchoopan.danialkala.data.model.requests.userCart.CartDataModelItem
import org.json.JSONObject

class CartVolleyRequest(private val m_context: Context) {
    private val userSharePreferences = UserSharePreferences(m_context)

    fun checkIfProductInUserCart(
        productId: String,
        requestResult: (success: Boolean) -> Unit
    ) {
        val str_request =
            object : StringRequest(
                Request.Method.GET, EndPoints.userCart + productId,
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


    fun userCart(
        requestResult: (
            success: Boolean,
            userCart: CartDataModel
        ) -> Unit
    ) {
        val str_request =
            object : StringRequest(
                Request.Method.GET, EndPoints.userCart,
                { strResponse ->
                    try {
                        val userCart = Gson().fromJson<CartDataModel>(strResponse, CartDataModel::class.java)
                        requestResult(true, userCart)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        requestResult(
                            false,
                            CartDataModel()
                        )
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


    fun addToCart(
        productId: String,
        requestResult: (success: Boolean) -> Unit
    ) {
        val str_request =
            object : StringRequest(
                Request.Method.POST, EndPoints.userCart,
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
                override fun getParams(): MutableMap<String, String> {
                    val requestParams = HashMap<String, String>()
                    requestParams["product_id"] = productId
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

    fun deleteCart(
        cartId: String,
        requestResult: (success: Boolean) -> Unit
    ) {
        val str_request =
            object : StringRequest(
                Request.Method.DELETE, EndPoints.userCart + cartId,
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