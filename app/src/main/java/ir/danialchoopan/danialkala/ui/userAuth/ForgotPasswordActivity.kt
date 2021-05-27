package ir.danialchoopan.danialkala.ui.userAuth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.api.volleyRequestes.auth.AuthUserVolleyRequest
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_user_register.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class ForgotPasswordActivity : AppCompatActivity() {
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        //setup toolbar
        toolbar_auth_title.text = "فراموشی رمز عبور"
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        val authUserVolleyRequest = AuthUserVolleyRequest(this@ForgotPasswordActivity)
        fotgot_btn_forgot_password.setOnClickListener {
            val loadingProcessDialog = LoadingProcessDialog(this@ForgotPasswordActivity).create()
            val phone = forgot_txt_phone.editText!!.text.toString()
            if (validateForgot()) {
                loadingProcessDialog.show()
                authUserVolleyRequest.checkPhoneForgotPassword(phone) { success ->
                    loadingProcessDialog.dismiss()
                    if (success) {
                        Intent(
                            this@ForgotPasswordActivity,
                            VerifyPhoneNumberForgotPasswordActivity::class.java
                        ).also { intent ->
                            intent.putExtra("intentUserPhone", phone)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            "کاربری با این شماره یافت نشد.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }
        }

        forgot_txt_phone.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                forgot_txt_phone.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun validateForgot(): Boolean {
        val phone = forgot_txt_phone.editText!!.text.toString()
        if (phone.isEmpty()) {
            forgot_txt_phone.error = "لطفا فید اجباری را پر کنید"
            forgot_txt_phone.isErrorEnabled = true
            return false
        }
        if (!phone.matches("(\\+98|0)?9\\d{9}".toRegex())) {
            forgot_txt_phone.error = "لطفا شماره همراه خود را برسی کنید"
            forgot_txt_phone.isErrorEnabled = true
            return false
        }
        return true
    }
}