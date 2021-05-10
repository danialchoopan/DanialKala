package ir.danialchoopan.danialkala.data.api.volleyRequestes

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.google.gson.Gson
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.data.model.requests.showCategory.ShowCategoryRequestDataModel

class ShowCategoryVolleyRequest(val m_context: Context) {
    fun sendRequest(
        categoryId: String,
        resultRequest: (showCategoryRequestDataModel: ShowCategoryRequestDataModel) -> Unit
    ) {
        val jsonRequest =
            JsonObjectRequest(Request.Method.GET, EndPoints.showCategory + categoryId, null,
                { jsonObj ->
                    val showCategoryModel = Gson().fromJson(
                        jsonObj.toString(),
                        ShowCategoryRequestDataModel::class.java
                    )
                    resultRequest(showCategoryModel)
                }, {
                    it.printStackTrace()
                }
            )
        VolleySingleTon.getInstance(m_context).addToRequestQueue(jsonRequest)
    }
}