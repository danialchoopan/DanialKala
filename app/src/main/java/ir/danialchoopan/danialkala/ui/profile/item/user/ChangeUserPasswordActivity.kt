package ir.danialchoopan.danialkala.ui.profile.item.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.api.volleyRequestes.auth.AuthUserVolleyRequest
import ir.danialchoopan.danialkala.ui.MainActivity
import kotlinx.android.synthetic.main.activity_change_user_password.*
import kotlinx.android.synthetic.main.activity_item_edit_profile.*
import kotlinx.android.synthetic.main.activity_item_edit_profile.edit_profile_layoutEtxt_name
import kotlinx.android.synthetic.main.activity_item_edit_profile.edit_profile_layoutEtxt_national_code
import kotlinx.android.synthetic.main.activity_item_edit_profile.edit_profile_layoutEtxt_phone

class ChangeUserPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_user_password)
        val authUserVolleyRequest = AuthUserVolleyRequest(this@ChangeUserPasswordActivity)
        chpassword_btn_change_password.setOnClickListener {
            if (validateAddressInput()) {
                val old_password = layoutEtxt_old_password_change.editText!!.text.toString()
                val password = layoutEtxt_password_change.editText!!.text.toString()
                authUserVolleyRequest.changePassword(old_password, password) { success, message ->
                    if (success) {
                        Toast.makeText(
                            this@ChangeUserPasswordActivity,
                            message, Toast.LENGTH_SHORT
                        ).show()
                        Intent(
                            this@ChangeUserPasswordActivity,
                            MainActivity::class.java
                        ).also { intent ->
                            startActivity(intent)
                        }
                        finishAffinity()
                    } else {
                        Toast.makeText(
                            this@ChangeUserPasswordActivity,
                            "مشکلی پیش آمده است لطفا بعدا امتحان کنید", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        //on text change fields
        layoutEtxt_old_password_change.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                layoutEtxt_old_password_change.isErrorEnabled = false
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
        layoutEtxt_re_password_change.editText!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                layoutEtxt_re_password_change.isErrorEnabled = false
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })//end

    }


    private fun validateAddressInput(): Boolean {
        val old_password = layoutEtxt_old_password_change.editText!!.text.toString()
        val password = layoutEtxt_password_change.editText!!.text.toString()
        val re_password = layoutEtxt_re_password_change.editText!!.text.toString()
        if (password.isEmpty()) {
            layoutEtxt_old_password_change.error = "لطفا فیلد ضرور را پر کنید"
            layoutEtxt_old_password_change.isErrorEnabled = true
            return false
        }
        if (old_password.isEmpty()) {
            layoutEtxt_password_change.error = "لطفا فیلد ضرور را پر کنید"
            layoutEtxt_password_change.isErrorEnabled = true
            return false
        }
        if (re_password.isEmpty()) {
            layoutEtxt_re_password_change.error = "لطفا فیلد ضرور را پر کنید"
            layoutEtxt_re_password_change.isErrorEnabled = true
            return false
        }
        if (password != re_password) {
            layoutEtxt_re_password_change.error = "رمز عبور جدید با تکرار آن برابر نیست"
            layoutEtxt_re_password_change.isErrorEnabled = true
            return false
        }
        return true
    }
}