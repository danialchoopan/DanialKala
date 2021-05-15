package ir.danialchoopan.danialkala.ui.userAuth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.data.api.volleyRequestes.auth.AuthUserVolleyRequest
import ir.danialchoopan.danialkala.ui.MainActivity
import kotlinx.android.synthetic.main.activity_phone_varify.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class PhoneVerifyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //check if user phone number verified
        val authUserVolleyRequest = AuthUserVolleyRequest(this@PhoneVerifyActivity)
        authUserVolleyRequest.checkIfPhoneVerified { verified ->
            if (verified) {
                Intent(this@PhoneVerifyActivity, MainActivity::class.java).also { intent ->
                    startActivity(intent)
                    finish()
                }
            } else {
                authUserVolleyRequest.requestSendValidationSms()
            }
        }
        //end check if user phone number verified
        setContentView(R.layout.activity_phone_varify)
        //close btn
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        //set toolbar title
        toolbar_auth_title.text = "تایید شماره همراه"


        //verify
        val ar_number_verify = arrayOf(
            R.id.verify_number_1,
            R.id.verify_number_2,
            R.id.verify_number_3,
            R.id.verify_number_4,
            R.id.verify_number_5,
            R.id.verify_number_6
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

        btn_verify_phone_user.setOnClickListener {
            progress_bar_verify_phone.visibility = View.VISIBLE
            val sumNumber =
                verify_number_1.text.toString() + verify_number_2.text.toString() + verify_number_3.text.toString() +
                        verify_number_4.text.toString() + verify_number_5.text.toString() + verify_number_6.text.toString()
            Log.i("Sum number ", sumNumber)
            authUserVolleyRequest.sendUserVerifySmsCode(sumNumber) { sendcode ->
                progress_bar_verify_phone.visibility = View.GONE
                when (sendcode.response_code) {
                    201 -> {
                        Intent(this@PhoneVerifyActivity, MainActivity::class.java).also { intent ->
                            startActivity(intent)
                        }
                    }
                    //invalid code
                    202 -> {
                        Toast.makeText(
                            this@PhoneVerifyActivity,
                            "کد شما اشتباه است",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    //code expire
                    203 -> {
                        Toast.makeText(
                            this@PhoneVerifyActivity,
                            "کد شما منقضی شده است",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }


        val counter_time = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val time_secend = millisUntilFinished / 1000
                phone_verify_count_time.text = "ارسال دوباره کد : ${time_secend}"
            }

            override fun onFinish() {

            }
        }
        counter_time.start()
    }
}