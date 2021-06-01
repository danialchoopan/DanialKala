package ir.danialchoopan.danialkala.ui.about

import android.R.id.message
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ir.danialchoopan.danialkala.R
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*


class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        //setup toolbar
        toolbar_auth_title.text = "درباره ما"
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        //btn send email
        about_btn_contact_us.setOnClickListener {
            val email = Intent(Intent.ACTION_SEND)
            email.putExtra(Intent.EXTRA_EMAIL, arrayOf("email@example.com"))
            email.putExtra(Intent.EXTRA_SUBJECT, "ارتباط با گروه ")
            email.putExtra(Intent.EXTRA_TEXT, "")
            email.type = "message/rfc822"
            startActivity(Intent.createChooser(email, "لطفا یک ایمیل کلاینت انتخاب کنید."))
        }
    }
}