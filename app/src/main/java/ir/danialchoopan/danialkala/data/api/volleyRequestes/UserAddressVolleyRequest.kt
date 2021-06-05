package ir.danialchoopan.danialkala.data.api.volleyRequestes

import android.content.Context
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.data.model.requests.userAddress.UserAddressDataModel
import ir.danialchoopan.danialkala.data.model.requests.userAddress.UserAddressDataModelItem
import org.json.JSONObject

class UserAddressVolleyRequest(val m_context: Context) {
    val userSharePreferences = UserSharePreferences(m_context)

    fun addUserAddress(
        state_name: String,
        city_name: String,
        city_code: String,
        post_code: String,
        lanline_phone: String,
        address_phone: String,
        address: String,
        resultRequest: (success: Boolean) -> Unit
    ) {
        val str_request = object : StringRequest(Method.POST, EndPoints.userAddress,
            { str_response ->
                val jsonResponse = JSONObject(str_response)
                resultRequest(jsonResponse.getBoolean("success"))
            },
            //error
            {
                it.printStackTrace()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val token_access = userSharePreferences.getToken()
                val requestHeaders = HashMap<String, String>()
                requestHeaders["Authorization"] = "Bearer $token_access";
                return requestHeaders
            }

            override fun getParams(): MutableMap<String, String> {
                val requestParams = HashMap<String, String>()
                requestParams["state_name"] = state_name
                requestParams["city_name"] = city_name
                requestParams["city_code"] = city_code
                requestParams["post_code"] = post_code
                requestParams["lanline_phone"] = lanline_phone
                requestParams["addess_phone"] = address_phone
                requestParams["address"] = address
                return requestParams
            }
        }
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }

    fun updateUserAddress(
        idUserAddressItem: String,
        state_name: String,
        city_name: String,
        city_code: String,
        post_code: String,
        lanline_phone: String,
        address_phone: String,
        address: String,
        resultRequest: (success: Boolean) -> Unit
    ) {
        val str_request =
            object : StringRequest(Method.PATCH, EndPoints.userAddress +"/"+ idUserAddressItem,
                { str_response ->
                    val jsonResponse = JSONObject(str_response)
                    resultRequest(jsonResponse.getBoolean("success"))
                },
                //error
                {
                    it.printStackTrace()
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val token_access = userSharePreferences.getToken()
                    val requestHeaders = HashMap<String, String>()
                    requestHeaders["Authorization"] = "Bearer $token_access";
                    return requestHeaders
                }

                override fun getParams(): MutableMap<String, String> {
                    val requestParams = HashMap<String, String>()
                    requestParams["state_name"] = state_name
                    requestParams["city_name"] = city_name
                    requestParams["city_code"] = city_code
                    requestParams["post_code"] = post_code
                    requestParams["lanline_phone"] = lanline_phone
                    requestParams["addess_phone"] = address_phone
                    requestParams["address"] = address
                    return requestParams
                }
            }
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }

    fun readUserAddress(
        resultRequest: (
            success: Boolean,
            userAddress: UserAddressDataModel
        ) -> Unit
    ) {
        val str_request = object : StringRequest(Method.GET, EndPoints.userAddress,
            { str_response ->
                try {
                    val gsonUserAddressDataModel =
                        Gson().fromJson(str_response, UserAddressDataModel::class.java)
                    resultRequest(true, gsonUserAddressDataModel)
                } catch (e: Exception) {
                    resultRequest(false, UserAddressDataModel())
                }
            },
            //error
            {
                it.printStackTrace()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val token_access = userSharePreferences.getToken()
                val requestHeaders = HashMap<String, String>()
                requestHeaders["Authorization"] = "Bearer $token_access";
                return requestHeaders
            }
        }
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }

    fun getUserAddress(
        idItem: String,
        resultRequest: (
            success: Boolean,
            userAddress: UserAddressDataModelItem
        ) -> Unit
    ) {
        val str_request = object : StringRequest(Method.GET, EndPoints.userAddress +"/"+ idItem,
            { str_response ->
                try {
                    val gsonUserAddressDataModelItem =
                        Gson().fromJson(str_response, UserAddressDataModelItem::class.java)
                    resultRequest(true, gsonUserAddressDataModelItem)
                } catch (e: Exception) {
                    resultRequest(
                        false, UserAddressDataModelItem(
                            "",
                            "",
                            "",
                            "",
                            "",
                            0,
                            "",
                            "",
                            "",
                            "",
                            0
                        )//end user address
                    )//end result request
                }
            },
            //error
            {
                it.printStackTrace()
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val token_access = userSharePreferences.getToken()
                val requestHeaders = HashMap<String, String>()
                requestHeaders["Authorization"] = "Bearer $token_access";
                return requestHeaders
            }
        }
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }


    fun deleteUserAddress(
        userAddressId: String,
        resultRequest: (success: Boolean) -> Unit
    ) {
        val str_request =
            object : StringRequest(Method.DELETE, EndPoints.userAddress +"/"+ userAddressId,
                { str_response ->
                    val jsonResponse = JSONObject(str_response)
                    resultRequest(jsonResponse.getBoolean("success"))
                },
                //error
                {
                    it.printStackTrace()
                }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val token_access = userSharePreferences.getToken()
                    val requestHeaders = HashMap<String, String>()
                    requestHeaders["Authorization"] = "Bearer $token_access";
                    return requestHeaders
                }
            }
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }
}