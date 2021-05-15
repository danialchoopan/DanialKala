package ir.danialchoopan.danialkala.data

import android.content.Context

class UserSharePreferences(private val m_context: Context) {
    val sharePreferences = m_context.getSharedPreferences("user_share", Context.MODE_PRIVATE)


    fun getToken(): String {
        return sharePreferences.getString("token", "").toString()
    }
}