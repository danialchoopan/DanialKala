package ir.danialchoopan.danialkala.data.api.volleyRequestes.state

import android.content.Context
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.data.model.requests.stetes.StatesRequestDataModel
import ir.danialchoopan.danialkala.data.model.requests.stetes.StatesRequestDataModelItem
import java.lang.Exception

class ShowStatesVolleyRequest(val m_context: Context) {
    fun getStates(
        States:(success: Boolean,
        statesRequestDataModel: StatesRequestDataModel)->Unit
    ) {
        val str_request = object : StringRequest(Method.GET, EndPoints.states,
            { str_response ->
                try {
                    val gsonStatesRequestDataModel =
                        Gson().fromJson(str_response, StatesRequestDataModel::class.java)
                    States(true, gsonStatesRequestDataModel)
                } catch (e: Exception) {
                    States(false, StatesRequestDataModel())
                }
            },
            //error
            {
                it.printStackTrace()
            }) {

        }
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }

    fun getCities(
        state_id: String,
        Cities:(success: Boolean,
        statesRequestDataModel: StatesRequestDataModel)->Unit
    ) {
        val str_request = object : StringRequest(Method.GET, EndPoints.states + state_id,
            { str_response ->
                try {
                    val gsonStatesRequestDataModel =
                        Gson().fromJson(str_response, StatesRequestDataModel::class.java)
                    Cities(true, gsonStatesRequestDataModel)
                } catch (e: Exception) {
                    Cities(false, StatesRequestDataModel())
                }
            },
            //error
            {
                it.printStackTrace()
            }) {

        }
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }


}