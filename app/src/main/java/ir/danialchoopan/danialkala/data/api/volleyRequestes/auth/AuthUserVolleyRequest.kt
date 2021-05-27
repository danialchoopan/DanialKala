package ir.danialchoopan.danialkala.data.api.volleyRequestes.auth

import android.content.Context
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.JsonObject
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.data.model.requests.editProfile.EditProfileDataModelRequest
import ir.danialchoopan.danialkala.data.model.requests.editProfile.UserInfo
import ir.danialchoopan.danialkala.data.model.requests.register.RegisterUserDataModel
import ir.danialchoopan.danialkala.data.model.requests.register.User
import ir.danialchoopan.danialkala.data.model.requests.editProfile.User as UserModelInfo
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
                        it.putInt("id", registerResult.user.id)
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
                    //if user verify his phone number remember the user
                    if (registerResult.user.phone_verified != "0") {
                        userSharePreferences.sharePreferences.edit().also {
                            it.putInt("id", registerResult.user.id)
                            it.putString("token", registerResult.token)
                            it.putString("name", registerResult.user.name)
                            it.putString("email", registerResult.user.email)
                            it.putString("phone", registerResult.user.phone)
                        }.apply()
                    }
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
        userToken: String = "",
        resultRequest: (verified: Boolean) -> Unit
    ) {
        val str_login_request = object : StringRequest(Method.POST, EndPoints.checkVerifyPhone,
            { strResponse ->
                try {
                    val jsonResult = JSONObject(strResponse)
                    resultRequest(jsonResult.getBoolean("verified"))
                } catch (e: Exception) {
                    resultRequest(false)
                }
            }
            //error
            , {
                it.printStackTrace()
            }) {

            override fun getHeaders(): MutableMap<String, String> {
                var token_access = ""
                token_access = if (userSharePreferences.getToken().isNotEmpty()) {
                    userSharePreferences.getToken()
                } else {
                    userToken
                }
                val requestHeaders = HashMap<String, String>()
                requestHeaders["Authorization"] = "Bearer $token_access";
                return requestHeaders

            }

        }//end request register
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_login_request)
    }

    fun requestSendValidationSms(userToken: String = "") {
        val str_send_validation_request =
            object : StringRequest(Method.POST, EndPoints.sendSmsVerifyUserCode,
                { strResponse ->

                }
                //error
                , {
                    it.printStackTrace()
                }) {

                override fun getHeaders(): MutableMap<String, String> {
                    var token_access = ""
                    token_access = if (userSharePreferences.getToken().isNotEmpty()) {
                        userSharePreferences.getToken()
                    } else {
                        userToken
                    }
                    val requestHeaders = HashMap<String, String>()
                    requestHeaders["Authorization"] = "Bearer $token_access";
                    return requestHeaders

                }

            }//end request register
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_send_validation_request)
    }

    fun sendUserVerifySmsCode(
        userToken: String,
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

                    var token_access = ""
                    token_access = if (userSharePreferences.getToken().isNotEmpty()) {
                        userSharePreferences.getToken()
                    } else {
                        userToken
                    }
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

    fun getUserData(
        resultRequest: (editProfileDataModelRequest: EditProfileDataModelRequest) -> Unit
    ) {
        val str_request = object : StringRequest(Method.POST, EndPoints.getUserInfo,
            { strResponse ->
                try {
                    val gsonEditProfileDataModelRequest =
                        Gson().fromJson(strResponse, EditProfileDataModelRequest::class.java)
                    resultRequest(gsonEditProfileDataModelRequest)
                } catch (e: Exception) {
                    resultRequest(
                        EditProfileDataModelRequest(
                            false,
                            UserModelInfo(
                                "",
                                "",
                                "",
                                1,
                                "",
                                "",
                                "",
                                "",
                                "",
                                UserInfo(
                                    "",
                                    "",
                                    "",
                                    1,
                                    "",
                                    "",
                                    "",
                                    "",
                                ),
                            )
                        )
                    )//end result request
                }//end catch
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
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }

    //update user info

    fun updateUserData(
        user_name: String,
        phone: String,
        national_code: String,
        state_name: String,
        city_name: String,
        city_code: String,
        resultRequest: (editProfileDataModelRequest: EditProfileDataModelRequest) -> Unit
    ) {
        val str_request = object : StringRequest(Method.PATCH, EndPoints.updateUserInfo,
            { strResponse ->
                try {
                    val gsonEditProfileDataModelRequest =
                        Gson().fromJson(strResponse, EditProfileDataModelRequest::class.java)
                    resultRequest(gsonEditProfileDataModelRequest)
                } catch (e: Exception) {
                    resultRequest(
                        EditProfileDataModelRequest(
                            false,
                            UserModelInfo(
                                "",
                                "",
                                "",
                                1,
                                "",
                                "",
                                "",
                                "",
                                "",
                                UserInfo(
                                    "",
                                    "",
                                    "",
                                    1,
                                    "",
                                    "",
                                    "",
                                    "",
                                ),
                            )
                        )
                    )//end result request
                }//end catch
            }
            //error
            , {
                it.printStackTrace()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val requestParam = HashMap<String, String>()
                requestParam["user_name"] = user_name
                requestParam["phone"] = phone
                requestParam["national_code"] = national_code
                requestParam["state_name"] = state_name
                requestParam["city_name"] = city_name
                requestParam["city_code"] = city_code
                return requestParam
            }

            override fun getHeaders(): MutableMap<String, String> {
                val token_access = userSharePreferences.getToken()
                val requestHeaders = HashMap<String, String>()
                requestHeaders["Authorization"] = "Bearer $token_access";
                return requestHeaders
            }

        }//end request register
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }

    fun sendVerifyEmail(
        resultRequest: (success: Boolean) -> Unit
    ) {
        val str_request = object : StringRequest(Method.POST, EndPoints.sendVerifyEmail,
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
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }

    //change password
    fun changePassword(
        oldPassword: String,
        newPassword: String,
        resultRequest: (success: Boolean, message: String) -> Unit
    ) {
        val str_request = object : StringRequest(Method.POST, EndPoints.changePassword,
            { strResponse ->
                val jsonResponse = JSONObject(strResponse)
                resultRequest(
                    jsonResponse.getBoolean("success"),
                    jsonResponse.getString("message")
                )
            }
            //error
            , {
                it.printStackTrace()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val requestParam = HashMap<String, String>()
                requestParam["oldPassword"] = oldPassword
                requestParam["newPassword"] = newPassword
                return requestParam
            }

            override fun getHeaders(): MutableMap<String, String> {
                val token_access = userSharePreferences.getToken()
                val requestHeaders = HashMap<String, String>()
                requestHeaders["Authorization"] = "Bearer $token_access";
                return requestHeaders
            }

        }//end request register
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }

    //phone check forgot password
    fun checkPhoneForgotPassword(
        phone: String,
        resultRequest: (success: Boolean) -> Unit
    ) {
        val str_request =
            object : StringRequest(Method.POST, EndPoints.checkUserPhoneForForgotPassword,
                { strResponse ->
                    try {

                        val jsonResponse = JSONObject(strResponse)
                        resultRequest(
                            jsonResponse.getBoolean("success")
                        )
                    } catch (e: Exception) {
                        resultRequest(false)
                    }
                }
                //error
                , {
                    it.printStackTrace()
                }) {

                override fun getParams(): MutableMap<String, String> {
                    val requestParam = HashMap<String, String>()
                    requestParam["phone"] = phone
                    return requestParam
                }

            }//end request register
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }

    fun confirmVerifyPhoneSmsForgotPassword(
        phone: String,
        code: String,
        resultRequest: (success: Boolean, responseCode: Int) -> Unit
    ) {
        val str_request =
            object : StringRequest(Method.POST, EndPoints.confirmVerifyPhoneSmsForgotPassword,
                { strResponse ->
                    try {

                        val jsonResponse = JSONObject(strResponse)
                        resultRequest(
                            jsonResponse.getBoolean("success"),
                            jsonResponse.getInt("response_code")
                        )
                    } catch (e: Exception) {
                        resultRequest(false, 0)
                    }
                }
                //error
                , {
                    it.printStackTrace()
                }) {

                override fun getParams(): MutableMap<String, String> {
                    val requestParam = HashMap<String, String>()
                    requestParam["phone"] = phone
                    requestParam["code"] = code
                    return requestParam
                }

            }//end request register
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }

    //request sms code to verify phone
    fun sendVerifyPhoneSmsForgotPassword(
        phone: String,
        resultRequest: (success: Boolean) -> Unit
    ) {
        val str_request =
            object : StringRequest(Method.POST, EndPoints.sendVerifyPhoneSmsForgotPassword,
                { strResponse ->
                    try {

                        val jsonResponse = JSONObject(strResponse)
                        resultRequest(
                            jsonResponse.getBoolean("success")
                        )
                    } catch (e: Exception) {
                        resultRequest(false)
                    }
                }
                //error
                , {
                    it.printStackTrace()
                }) {

                override fun getParams(): MutableMap<String, String> {
                    val requestParam = HashMap<String, String>()
                    requestParam["phone"] = phone
                    return requestParam
                }

            }//end request register
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }

    //change password forgot password
    fun changePasswordForgot(
        password: String,
        phone: String,
        resultRequest: (success: Boolean, message: String) -> Unit
    ) {
        val str_request = object : StringRequest(Method.POST, EndPoints.changePasswordForgot,
            { strResponse ->
                val jsonResponse = JSONObject(strResponse)
                resultRequest(
                    jsonResponse.getBoolean("success"),
                    jsonResponse.getString("message")
                )
            }
            //error
            , {
                it.printStackTrace()
            }) {

            override fun getParams(): MutableMap<String, String> {
                val requestParam = HashMap<String, String>()
                requestParam["password"] = password
                requestParam["phone"] = phone
                return requestParam
            }

        }//end request register
        VolleySingleTon.getInstance(m_context).addToRequestQueue(str_request)
    }

}