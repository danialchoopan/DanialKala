package ir.danialchoopan.danialkala.data.api.volleyRequestes.order

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.data.model.requests.orders.OrderDataModel
import ir.danialchoopan.danialkala.data.model.requests.productOrder.ProductOrder
import org.json.JSONObject

class OrderVolleyRequest(private val m_context: Context) {
    private val userSharePreferences = UserSharePreferences(m_context)


    fun addOrder(
        amount: Int, address_id: Int, description: String,
        resultRequest: (success: Boolean, urlIdPay: String) -> Unit
    ) {

        val str_request =
            object : StringRequest(
                Request.Method.POST, EndPoints.addOrder,
                { strResponse ->
                    try {
                        val jsonResult = JSONObject(strResponse)
                        resultRequest(true, jsonResult.getString("link"))
                    } catch (e: Exception) {
                        e.printStackTrace()
                        resultRequest(false, "")
                    }
                },
                //error
                { error ->
                    error.printStackTrace()
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val requestParams = HashMap<String, String>()
                    requestParams["amount"] = amount.toString()
                    requestParams["address_id"] = address_id.toString()
                    requestParams["description"] = description
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

    fun getUserOrders(
        resultRequest: (success: Boolean, orders: OrderDataModel) -> Unit
    ) {

        val str_request =
            object : StringRequest(
                Request.Method.GET, EndPoints.addOrder,
                { strResponse ->
                    try {
                        val gsonOrders = Gson().fromJson(strResponse, OrderDataModel::class.java)
                        resultRequest(true, gsonOrders)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        resultRequest(false, OrderDataModel())
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

    fun getUserOrdersProducts(
        order_number: String,
        resultRequest: (success: Boolean, orderProduct: ProductOrder) -> Unit
    ) {
        val str_request =
            object : StringRequest(
                Request.Method.GET, EndPoints.addOrder +"/"+ order_number,
                { strResponse ->
                    try {
                        val gsonOrdersProduct =
                            Gson().fromJson(strResponse, ProductOrder::class.java)
                        resultRequest(true, gsonOrdersProduct)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        resultRequest(false, ProductOrder())
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