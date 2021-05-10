package ir.danialchoopan.danialkala.data.api.volleyRequestes

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.data.model.requests.showProductPropertise.PropertiesProduct
import ir.danialchoopan.danialkala.data.model.requests.showProductPropertise.ShowProductPropertiesDataModelGson
import ir.danialchoopan.danialkala.data.model.requests.showProductPropertise.SubPropertiesProduct

class ShowProductPropertiesVolleyRequest(private val m_context: Context) {

    fun sendRequest(
        productId: String,
        requestResult: (success: Boolean, productPropertiesDataModel: ShowProductPropertiesDataModelGson) -> Unit
    ) {
        val stringRequest = StringRequest(
            Request.Method.GET,
            "${EndPoints.baseUrl}product/${productId}/properties",
            //success
            { strResult ->
                try {
                    val gsonParse =
                        Gson().fromJson(strResult, ShowProductPropertiesDataModelGson::class.java)
                    requestResult(true, gsonParse)
                } catch (e: Exception) {
                    requestResult(
                        false, ShowProductPropertiesDataModelGson(
                            "",
                            "",
                            "",
                            "",
                            0,
                            "",
                            emptyList<PropertiesProduct>(),
                            "",
                            "",
                            "",
                        )
                    )
                }

            },
            //error
            { volleyError ->
                volleyError.printStackTrace()

            }
        )//end request
        VolleySingleTon.getInstance(m_context).addToRequestQueue(stringRequest)
    }
}