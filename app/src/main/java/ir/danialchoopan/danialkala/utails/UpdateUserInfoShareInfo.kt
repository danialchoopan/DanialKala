package ir.danialchoopan.danialkala.utails

import android.content.Context
import android.widget.Toast
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.data.api.volleyRequestes.auth.AuthUserVolleyRequest

class UpdateUserInfoShareInfo(private val m_context: Context) {
    fun instance() {
        val userSharePreferences =
            UserSharePreferences(m_context).sharePreferences
        val authUserVolleyRequest = AuthUserVolleyRequest(m_context)
        authUserVolleyRequest.getUserData { editProfileDataModelRequest ->
            if (editProfileDataModelRequest.success) {
                userSharePreferences.edit().also { editor ->
                    editor.putString("name", editProfileDataModelRequest.user.name)
                    editor.putString("phone", editProfileDataModelRequest.user.phone)
                }.apply()
            } else {
                Toast.makeText(
                    m_context,
                    "مشکلی در بروزسانی اطلاعات کاربر پیش آمده",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}