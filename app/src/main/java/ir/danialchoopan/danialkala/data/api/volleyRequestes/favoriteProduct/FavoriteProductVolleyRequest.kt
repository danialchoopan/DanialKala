package ir.danialchoopan.danialkala.data.api.volleyRequestes.favoriteProduct

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.data.model.requests.favoriteProduct.FavoriteProducts
import org.json.JSONObject

class FavoriteProductVolleyRequest(private val m_context: Context) {
    private val userSharePreferences = UserSharePreferences(m_context)


    fun checkIfProductLiked(
        productId: String,
        requestResult: (favorite: Boolean) -> Unit
    ) {
        val str_request =
            object : StringRequest(Request.Method.POST, EndPoints.favoriteProductCheck,
                { strResponse ->
                    try {
                        val jsonResult = JSONObject(strResponse)
                        requestResult(jsonResult.getBoolean("favorite"))
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

    fun likeProduct(
        productId: String,
        requestResult: (success: Boolean) -> Unit
    ) {
        val str_request =
            object : StringRequest(Request.Method.POST, EndPoints.likeProduct,
                { strResponse ->
                    val jsonResult = JSONObject(strResponse)
                    requestResult(jsonResult.getBoolean("favorite"))
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

    fun getAllProducts(requestResult: (success: Boolean, favoriteProduct: FavoriteProducts) -> Unit) {

        val str_request =
            object : StringRequest(Request.Method.POST, EndPoints.allFavoriteProduct,
                { strResponse ->
                    try {
                        val favoriteProduct =
                            Gson().fromJson(strResponse, FavoriteProducts::class.java)
                        requestResult(true, favoriteProduct)
                    } catch (e: Exception) {
                        requestResult(false, FavoriteProducts())
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