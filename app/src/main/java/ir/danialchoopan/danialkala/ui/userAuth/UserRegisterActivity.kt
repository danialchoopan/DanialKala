package ir.danialchoopan.danialkala.ui.userAuth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.api.volleyRequestes.auth.AuthUserVolleyRequest
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import kotlinx.android.synthetic.main.activity_user_register.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*
import java.util.regex.Pattern

class UserRegisterActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_user_register)
        //close btn
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        //set toolbar title
        toolbar_auth_title.text = "نام نویسی"


        //open login activity
        tv_open_login.setOnClickListener {
            Intent(this@UserRegisterActivity, UserLoginActivity::class.java).also { intent ->
                startActivity(intent)
                finish()
            }
        }

        //btn register user
        btn_register_user.setOnClickListener {
            val loadingDialogRegister = LoadingProcessDialog(this@UserRegisterActivity).create()
            val authUserRequest = AuthUserVolleyRequest(this@UserRegisterActivity)
            if (checkUserInputs()) {
                loadingDialogRegister.show()
                val e_name = layoutEtxt_name.editText!!.text.toString()
                val e_email = layoutEtxt_email.editText!!.text.toString()
                val e_password = layoutEtxt_password.editText!!.text.toString()
                val e_phone = layoutEtxt_phone.editText!!.text.toString()
                authUserRequest.register(
                    e_name,
                    e_email,
                    e_phone,
                    e_password
                ) { success, registerUserDataModel ->
                    loadingDialogRegister.dismiss()
                    if (success) {
                        Toast.makeText(
                            this@UserRegisterActivity,
                            "نام نویسی شما با موفقیت انجام شد",
                            Toast.LENGTH_SHORT
                        ).show()
                        //show toast welcome
                        Intent(
                            this@UserRegisterActivity,
                            PhoneVerifyActivity::class.java
                        ).also { intent ->
                            intent.putExtra("intentUserId", registerUserDataModel.user.id)
                            intent.putExtra("intentUserToken", registerUserDataModel.token)
                            intent.putExtra("intentUserName", registerUserDataModel.user.name)
                            intent.putExtra("intentUserEmail", registerUserDataModel.user.email)
                            intent.putExtra("intentUserPhone", registerUserDataModel.user.phone)
                            startActivity(intent)
                            finish()
                        }//end intent
                    } else {
                        Toast.makeText(
                            this@UserRegisterActivity,
                            getString(R.string.errorConnectionSnackBar),
                            Toast.LENGTH_SHORT
                        ).show()
                        //show toast error
                    }
                }
            }//end if
        }//end btn register


        //text change watcher edit texts
        layoutEtxt_name.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                layoutEtxt_name.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })//end
        layoutEtxt_email.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                layoutEtxt_email.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })//end
        layoutEtxt_phone.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                layoutEtxt_phone.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })//end
        layoutEtxt_password.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                layoutEtxt_password.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })//end

    }

    private fun checkUserInputs(): Boolean {
        val e_name = layoutEtxt_name.editText!!.text.toString()
        val e_email = layoutEtxt_email.editText!!.text.toString()
        val e_password = layoutEtxt_password.editText!!.text.toString()
        val e_phone = layoutEtxt_phone.editText!!.text.toString()
        if (e_name.isEmpty()) {
            layoutEtxt_name.isErrorEnabled = true
            layoutEtxt_name.error = "لطفا این فیلد را پر کلید."
            return false
        }
        if (e_email.isEmpty()) {
            layoutEtxt_email.isErrorEnabled = true
            layoutEtxt_email.error = "لطفا این فیلد را پر کلید."
            return false
        }
        if (e_password.isEmpty()) {
            layoutEtxt_password.isErrorEnabled = true
            layoutEtxt_password.error = "لطفا این فیلد را پر کلید."
            return false
        }
        if (e_phone.isEmpty()) {
            layoutEtxt_phone.isErrorEnabled = true
            layoutEtxt_phone.error = "لطفا این فیلد را پر کلید."
            return false
        }
        if (!e_phone.matches("(\\+98|0)?9\\d{9}".toRegex())) {
            layoutEtxt_phone.isErrorEnabled = true
            layoutEtxt_phone.error = "لطفا شماره همراه خود را برسی کنید."
            return false
        }
        if (!e_email.matches(EMAIL_ADDRESS_PATTERN.toRegex())) {
            layoutEtxt_email.isErrorEnabled = true
            layoutEtxt_email.error = "لطفا پست الکترونیک خود را برسی کنید."
            return false
        }
        return true
    }
}