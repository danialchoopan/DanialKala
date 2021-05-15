package ir.danialchoopan.danialkala.ui.userAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.api.volleyRequestes.auth.AuthUserVolleyRequest
import kotlinx.android.synthetic.main.activity_user_login.*
import kotlinx.android.synthetic.main.activity_user_login.view.*
import kotlinx.android.synthetic.main.activity_user_register.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class UserLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)
        //close btn
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        //set toolbar title
        toolbar_auth_title.text = "ورود"

        tv_open_register.setOnClickListener {
            Intent(this@UserLoginActivity, UserRegisterActivity::class.java).also { intent ->
                startActivity(intent)
                finish()
            }
        }
        btn_login_user.setOnClickListener {
            val authUserRequest = AuthUserVolleyRequest(this@UserLoginActivity)
            if (checkUserInputs()) {
                val e_email = layoutEtxt_email_login.editText!!.text.toString()
                val e_password = layoutEtxt_password_login.editText!!.text.toString()
                authUserRequest.login(
                    e_email,
                    e_password
                ) { success, loginUserDataModel ->
                    if (success) {
                        Toast.makeText(
                            this@UserLoginActivity,
                            "ورود با موفقیت انجام شد",
                            Toast.LENGTH_SHORT
                        ).show()
                        //show toast welcome
                        Intent(
                            this@UserLoginActivity,
                            PhoneVerifyActivity::class.java
                        ).also { intent ->
                            startActivity(intent)
                        }//end intent
                    } else {
                        Toast.makeText(
                            this@UserLoginActivity,
                            "نام کاربری یا زمرعبور وارد شده اشتباه است لطفا دوباره سعی کنید",
                            Toast.LENGTH_SHORT
                        ).show()
                        //show toast error
                    }
                }
            }//end if
        }

        //clear errors
        layoutEtxt_email_login.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                layoutEtxt_email_login.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })//end
        layoutEtxt_password_login.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                layoutEtxt_password_login.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })//end
    }


    private fun checkUserInputs(): Boolean {
        val e_email = layoutEtxt_email_login.editText!!.text.toString()
        val e_password = layoutEtxt_password_login.editText!!.text.toString()
        if (
            e_email.isEmpty() &&
            e_password.isEmpty()
        ) {
            if (e_email.isEmpty()) {
                layoutEtxt_email_login.isErrorEnabled = true
                layoutEtxt_email_login.error = "لطفا این فیلد را پر کلید."
            }
            if (e_password.isEmpty()) {
                layoutEtxt_password_login.isErrorEnabled = true
                layoutEtxt_password_login.error = "لطفا این فیلد را پر کلید."
            }
            return false
        } else {
            return true
        }
    }
}