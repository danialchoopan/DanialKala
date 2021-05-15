package ir.danialchoopan.danialkala.data.api.volleyRequestes.auth

import android.content.Context
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.data.model.requests.editProfile.EditProfileDataModelRequest
import ir.danialchoopan.danialkala.data.model.requests.register.RegisterUserDataModel
import ir.danialchoopan.danialkala.data.model.requests.register.User
import ir.danialchoopan.danialkala.data.model.requests.sendCodeSms.SendCodeSMS
import org.json.JSONObject

class AuthUserVolleyRequest(private val m_context: Context) {
    private val userSharePreferences = UserSharePreferences(m_context)
    fun register(
        name: String,
        email: String,
        phone: String,
        password: String,
        resultRequest: (success: Boolean, registerUserDataModel: RegisterUserDataModel) -> Unit
    ) {
        val str_register_request = object : StringRequest(Method.POST, EndPoints.register,
            { strResponse ->
                try {
                    val registerResult =
                        Gson().fromJson(strResponse, RegisterUserDataModel::class.java)
                    resultRequest(true, registerResult)
                    userSharePreferences.sharePreferences.edit().also {
                        it.putString("token", registerResult.token)
                        it.putString("name", registerResult.user.name)
                        it.putString("email", registerResult.user.email)
                        it.putString("phone", registerResult.user.phone)
                    }.apply()
                } catch (e: Exception) {
                    resultRequest(
                        false, RegisterUserDataModel(
                            "",
                            false,
                            "",
                            User(
                                "",
                                "",
                                "",
                                1,
                                "",
                                "",
                                "",
                                "",
                                "",
                            )
                        )
                    )
                }
            }
            //error
            , {
                it.printStackTrace()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val m_params = HashMap<String, String>()
                m_params["name"] = name
                m_params["email"] = email
                m_params["phone"] = phone
                m_params["password"] = password
                return m_params
            }

        }//end request register
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_register_request)
    }

    fun login(
        email: String,
        password: String,
        resultRequest: (success: Boolean, loginUserDataModel: RegisterUserDataModel) -> Unit
    ) {

        val str_login_request = object : StringRequest(Method.POST, EndPoints.login,
            { strResponse ->
                val success = JSONObject(strResponse).getBoolean("success")
                try {

                    val registerResult =
                        Gson().fromJson(strResponse, RegisterUserDataModel::class.java)
                    resultRequest(success, registerResult)
                    userSharePreferences.sharePreferences.edit().also {
                        it.putString("token", registerResult.token)
                        it.putString("name", registerResult.user.name)
                        it.putString("email", registerResult.user.email)
                        it.putString("phone", registerResult.user.phone)
                    }.apply()

                } catch (e: Exception) {
                    resultRequest(
                        success, RegisterUserDataModel(
                            "",
                            false,
                            "",
                            User(
                                "",
                                "",
                                "",
                                1,
                                "",
                                "",
                                "",
                                "",
                                "",
                            )
                        )
                    )
                }
            }
            //error
            , {
                it.printStackTrace()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val m_params = HashMap<String, String>()
                m_params["email"] = email
                m_params["password"] = password
                return m_params
            }

        }//end request register
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_login_request)
    }

    fun checkIfPhoneVerified(
        resultRequest: (verified: Boolean) -> Unit
    ) {
        val str_login_request = object : StringRequest(Method.POST, EndPoints.checkVerifyPhone,
            { strResponse ->
                val jsonResult = JSONObject(strResponse)
                resultRequest(jsonResult.getBoolean("verified"))
            }
            //error
            , {
                it.printStackTrace()
            }) {

            override fun getHeaders(): MutableMap<String, String> {
                val token_access = userSharePreferences.getToken()
                val requestHeaders = HashMap<String, String>()
                requestHeaders["Authorization"] = "Bearer $token_access";
                return requestHeaders

            }

        }//end request register
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_login_request)
    }

    fun requestSendValidationSms() {
        val str_send_validation_request =
            object : StringRequest(Method.POST, EndPoints.sendSmsVerifyUserCode,
                { strResponse ->

                }
                //error
                , {
                    it.printStackTrace()
                }) {

                override fun getHeaders(): MutableMap<String, String> {
                    val token_access = userSharePreferences.getToken()
                    val requestHeaders = HashMap<String, String>()
                    requestHeaders["Authorization"] = "Bearer $token_access";
                    return requestHeaders

                }

            }//end request register
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_send_validation_request)
    }

    fun sendUserVerifySmsCode(
        code: String,
        resultRequest: (sendCodeSMS: SendCodeSMS) -> Unit
    ) {
        val str_send_code_validation_request =
            object : StringRequest(Method.POST, EndPoints.confirmVerifyPhoneSms,
                { strResponse ->
                    val sendSmsCodeModel = Gson().fromJson(strResponse, SendCodeSMS::class.java)
                    resultRequest(sendSmsCodeModel)
                }
                //error
                , {
                    it.printStackTrace()
                }) {

                override fun getParams(): MutableMap<String, String> {
                    val m_params = HashMap<String, String>()
                    m_params["user_code"] = code
                    return m_params
                }

                override fun getHeaders(): MutableMap<String, String> {
                    val token_access = userSharePreferences.getToken()
                    val requestHeaders = HashMap<String, String>()
                    requestHeaders["Authorization"] = "Bearer $token_access";
                    return requestHeaders

                }

            }//end request register
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_send_code_validation_request)
    }


    fun checkToken(
        resultRequest: (success: Boolean) -> Unit
    ) {
        val str_token_request = object : StringRequest(Method.POST, EndPoints.tokenCheck,
            { strResponse ->
                val jsonResult = JSONObject(strResponse)
                resultRequest(jsonResult.getBoolean("success"))
            }
            //error
            , {
                it.printStackTrace()
            }) {

            override fun getHeaders(): MutableMap<String, String> {
                val token_access = userSharePreferences.getToken()
                val requestHeaders = HashMap<String, String>()
                requestHeaders["Authorization"] = "Bearer $token_access";
                return requestHeaders

            }

        }//end request register
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_token_request)
    }

    //----------
    fun getUserData(
        resultRequest: (editProfileDataModelRequest: EditProfileDataModelRequest) -> Unit
    ) {
        val str_token_request = object : StringRequest(Method.POST, EndPoints.checkVerifyPhone,
            { strResponse ->
                val jsonResult = JSONObject(strResponse)
                resultRequest(jsonResult.getBoolean("success"))
            }
            //error
            , {
                it.printStackTrace()
            }) {

            override fun getHeaders(): MutableMap<String, String> {
                val token_access = userSharePreferences.getToken()
                val requestHeaders = HashMap<String, String>()
                requestHeaders["Authorization"] = "Bearer $token_access";
                return requestHeaders

            }

        }//end request register
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_token_request)
    }
}