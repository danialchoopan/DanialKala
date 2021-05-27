package ir.danialchoopan.danialkala.ui.userAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.api.volleyRequestes.auth.AuthUserVolleyRequest
import ir.danialchoopan.danialkala.ui.MainActivity
import kotlinx.android.synthetic.main.activity_reset_password_forgot_password.*

class ResetPasswordForgotPasswordActivity : AppCompatActivity() {
    var intentUserPhone = ""
    lateinit var authUserVolleyRequest: AuthUserVolleyRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password_forgot_password)
        intentUserPhone = intent.extras!!.getString("intentUserPhone", "")
        authUserVolleyRequest = AuthUserVolleyRequest(this@ResetPasswordForgotPasswordActivity)

        change_forgot_btn_password_change.setOnClickListener {
            if (validatePassword()) {
                val password = change_forgot_txt_new_password.editText!!.text.toString()
                authUserVolleyRequest.changePasswordForgot(
                    password,
                    intentUserPhone
                ) { success, message ->
                    if (success) {
                        Intent(
                            this@ResetPasswordForgotPasswordActivity,
                            MainActivity::class.java
                        ).also { intent ->
                            startActivity(intent)
                            finishAffinity()
                        }
                    }
                    Toast.makeText(
                        this@ResetPasswordForgotPasswordActivity,
                        message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        change_forgot_txt_new_password.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                change_forgot_txt_new_password.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        change_forgot_txt_re_new_password.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                change_forgot_txt_re_new_password.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun validatePassword(): Boolean {
        val password = change_forgot_txt_new_password.editText!!.text.toString()
        val re_password = change_forgot_txt_re_new_password.editText!!.text.toString()
        if (password.isEmpty()) {
            change_forgot_txt_new_password.error = "لطفا فیلد اجباری را پر کنید"
            change_forgot_txt_new_password.isErrorEnabled = true
            return false
        }
        if (re_password.isEmpty()) {
            change_forgot_txt_re_new_password.error = "لطفا فیلد اجباری را پر کنید"
            change_forgot_txt_re_new_password.isErrorEnabled = true
            return false
        }
        if (password != re_password) {
            change_forgot_txt_new_password.error = "رمزعبور شما با تکرار آن برار نیست"
            change_forgot_txt_new_password.isErrorEnabled = true
            return false
        }
        return true
    }
}