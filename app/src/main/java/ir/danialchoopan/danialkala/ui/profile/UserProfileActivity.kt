package ir.danialchoopan.danialkala.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.ui.profile.item.ItemEditProfileActivity
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        val userSharePreferences = UserSharePreferences(this@UserProfileActivity).sharePreferences
        //toolbar
        toolbar_auth_title.text = "پروفایل"
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        //end toolbar
        profile_tv_name.text = userSharePreferences.getString("name", "")
        profile_tv_phone.text = userSharePreferences.getString("phone", "")

        //profiles items
        profile_item_exit.setOnClickListener {
            userSharePreferences.edit().clear().apply()
            finish()
        }
        profile_item_edit_profile.setOnClickListener {
            Intent(this@UserProfileActivity, ItemEditProfileActivity::class.java).also { intent ->
                startActivity(intent)
            }
        }
    }
}