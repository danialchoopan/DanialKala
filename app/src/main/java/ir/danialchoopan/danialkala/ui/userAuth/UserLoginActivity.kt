package ir.danialchoopan.danialkala.ui.userAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.api.volleyRequestes.auth.AuthUserVolleyRequest
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import ir.danialchoopan.danialkala.ui.MainActivity
import kotlinx.android.synthetic.main.activity_user_login.*
import kotlinx.android.synthetic.main.activity_user_login.view.*
import kotlinx.android.synthetic.main.activity_user_register.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*
import java.util.regex.Pattern

class UserLoginActivity : AppCompatActivity() {
    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

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
        tv_open_forgot_password.setOnClickListener {
            Intent(this@UserLoginActivity, ForgotPasswordActivity::class.java).also { intent ->
                startActivity(intent)
            }
        }
        chpassword_btn_change_password.setOnClickListener {
            val loadingDialogLogin = LoadingProcessDialog(this@UserLoginActivity).create()
            loadingDialogLogin.show()
            val authUserRequest = AuthUserVolleyRequest(this@UserLoginActivity)
            if (checkUserInputs()) {
                val e_email = layoutEtxt_email_login.editText!!.text.toString()
                val e_password = layoutEtxt_password_change.editText!!.text.toString()
                authUserRequest.login(
                    e_email,
                    e_password
                ) { success, loginUserDataModel ->
                    loadingDialogLogin.dismiss()
                    if (success) {
                        Toast.makeText(
                            this@UserLoginActivity,
                            "ورود با موفقیت انجام شد",
                            Toast.LENGTH_SHORT
                        ).show()
                        //show toast welcome
                        val loadingDialogCheckPhone =
                            LoadingProcessDialog(this@UserLoginActivity).create()
                        loadingDialogCheckPhone.show()
                        authUserRequest.checkIfPhoneVerified { verified ->
                            loadingDialogCheckPhone.dismiss()
                            if (verified) {
                                Intent(
                                    this@UserLoginActivity,
                                    MainActivity::class.java
                                ).also { intent ->
                                    startActivity(intent)
                                    finishAffinity()
                                }
                            } else {
                                Intent(
                                    this@UserLoginActivity,
                                    PhoneVerifyActivity::class.java
                                ).also { intent ->
                                    intent.putExtra("intentUserId", loginUserDataModel.user.id)
                                    intent.putExtra("intentUserToken", loginUserDataModel.token)
                                    intent.putExtra("intentUserName", loginUserDataModel.user.name)
                                    intent.putExtra(
                                        "intentUserEmail",
                                        loginUserDataModel.user.email
                                    )
                                    intent.putExtra(
                                        "intentUserPhone",
                                        loginUserDataModel.user.phone
                                    )
                                    startActivity(intent)
                                    finish()
                                }//end intent
                            }
                        }
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
        layoutEtxt_password_change.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                layoutEtxt_password_change.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })//end
    }


    private fun checkUserInputs(): Boolean {
        val e_email = layoutEtxt_email_login.editText!!.text.toString()
        val e_password = layoutEtxt_password_change.editText!!.text.toString()
        if (e_email.isEmpty()) {
            layoutEtxt_email_login.isErrorEnabled = true
            layoutEtxt_email_login.error = "لطفا این فیلد را پر کلید."
            return false
        }
        if (e_password.isEmpty()) {
            layoutEtxt_password_change.isErrorEnabled = true
            layoutEtxt_password_change.error = "لطفا این فیلد را پر کلید."
            return false
        }
        if (!e_email.matches(EMAIL_ADDRESS_PATTERN.toRegex())) {
            layoutEtxt_email_login.isErrorEnabled = true
            layoutEtxt_email_login.error = "لطفا پست الکترونیک خود را برسی کنید."
            return false
        }
        return true
    }
}