package ir.danialchoopan.danialkala.ui.userAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.api.volleyRequestes.auth.AuthUserVolleyRequest
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import ir.danialchoopan.danialkala.ui.MainActivity
import kotlinx.android.synthetic.main.activity_phone_varify.*
import kotlinx.android.synthetic.main.activity_verify_phone_number_forgot_password.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class VerifyPhoneNumberForgotPasswordActivity : AppCompatActivity() {
    var intentUserPhone: String = ""
    lateinit var authUserVolleyRequest: AuthUserVolleyRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_phone_number_forgot_password)
        intentUserPhone = intent.extras!!.getString("intentUserPhone", "")
        authUserVolleyRequest = AuthUserVolleyRequest(this@VerifyPhoneNumberForgotPasswordActivity)
        authUserVolleyRequest.sendVerifyPhoneSmsForgotPassword(intentUserPhone) { success ->

        }
        toolbar_auth_title.text = "تایید شماره همراه"
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        fpasswd_phone_verify_tv.append("\n برای شماره : $intentUserPhone ")
        //verify
        val ar_number_verify = arrayOf(
            R.id.fpasswd_verify_number_1,
            R.id.fpasswd_verify_number_2,
            R.id.fpasswd_verify_number_3,
            R.id.fpasswd_verify_number_4,
            R.id.fpasswd_verify_number_5,
            R.id.fpasswd_verify_number_6
        )
        for (i in ar_number_verify.indices) {
            val j = i + 1
            val txt1 = findViewById<EditText>(ar_number_verify[i])
            if (ar_number_verify.size - 1 != i) {
                val txt2 = findViewById<EditText>(ar_number_verify[j])
                txt1.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {

                    }

                    override fun afterTextChanged(s: Editable?) {
                        txt2.requestFocus()
                    }

                })

            }

        }//end for txt
        val loadingDialogPhoneVerifyForgot =
            LoadingProcessDialog(this@VerifyPhoneNumberForgotPasswordActivity).create()
        fpasswd_btn_verify_phone_user.setOnClickListener {
            val sumNumber =
                fpasswd_verify_number_1.text.toString() + fpasswd_verify_number_2.text.toString() + fpasswd_verify_number_3.text.toString() +
                        fpasswd_verify_number_4.text.toString() + fpasswd_verify_number_5.text.toString() + fpasswd_verify_number_6.text.toString()
            loadingDialogPhoneVerifyForgot.show()
            authUserVolleyRequest.confirmVerifyPhoneSmsForgotPassword(
                intentUserPhone,
                sumNumber
            ) { success, responseCode ->
                loadingDialogPhoneVerifyForgot.dismiss()
                when (responseCode) {
                    201 -> {
                        Intent(
                            this@VerifyPhoneNumberForgotPasswordActivity,
                            ResetPasswordForgotPasswordActivity::class.java
                        ).also { intent ->
                            intent.putExtra("intentUserPhone", intentUserPhone)
                            startActivity(intent)

                            Toast.makeText(
                                this@VerifyPhoneNumberForgotPasswordActivity,
                                "کد شما تایید شد.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    //invalid code
                    202 -> {
                        Toast.makeText(
                            this@VerifyPhoneNumberForgotPasswordActivity,
                            "کد شما اشتباه است",
                            Toast.LENGTH_SHORT
                        ).show()
                        clearTextFields()
                    }
                    //code expire
                    203 -> {
                        Toast.makeText(
                            this@VerifyPhoneNumberForgotPasswordActivity,
                            "کد شما منقضی شده است",
                            Toast.LENGTH_SHORT
                        ).show()
                        clearTextFields()
                    }
                }
            }
        }

    }


    private fun clearTextFields() {
        fpasswd_verify_number_1.setText("")
        fpasswd_verify_number_2.setText("")
        fpasswd_verify_number_3.setText("")
        fpasswd_verify_number_4.setText("")
        fpasswd_verify_number_5.setText("")
        fpasswd_verify_number_6.setText("")
        fpasswd_verify_number_1.requestFocus()
    }
}