package ir.danialchoopan.danialkala.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.data.api.volleyRequestes.auth.AuthUserVolleyRequest
import ir.danialchoopan.danialkala.ui.profile.item.ItemEditProfileActivity
import ir.danialchoopan.danialkala.ui.profile.item.favoriteProduct.FavoriteProductIndexActivity
import ir.danialchoopan.danialkala.ui.profile.item.userAddress.UserAddressIndexActivity
import ir.danialchoopan.danialkala.utails.UpdateUserInfoShareInfo
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        //update user info
        UpdateUserInfoShareInfo(this@UserProfileActivity).instance()
        //end update user info
        val userSharePreferences = UserSharePreferences(this@UserProfileActivity).sharePreferences
        val authUserVolleyRequest = AuthUserVolleyRequest(this@UserProfileActivity)
        authUserVolleyRequest.getUserData { editProfileDataModelRequest ->
            profile_loading_email.visibility = View.GONE
            if (editProfileDataModelRequest.success) {
                if (editProfileDataModelRequest.user.email_verified_at == null) {
                    profile_validate_email_layout.visibility = View.VISIBLE
                } else {
                    profile_validate_email_layout.visibility = View.GONE
                }
            }
        }
        profile_btn_send_email.setOnClickListener {
            profile_progress_bar_send_email.visibility = View.VISIBLE
            profile_btn_send_email.visibility = View.GONE
            authUserVolleyRequest.sendVerifyEmail { success ->
                profile_progress_bar_send_email.visibility = View.GONE
                if (success) {
                    Toast.makeText(
                        this@UserProfileActivity,
                        "پست الکترونیک خود را برسی کنید",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
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

        profile_item_address.setOnClickListener {
            Intent(this@UserProfileActivity, UserAddressIndexActivity::class.java).also { intent ->
                startActivity(intent)
            }
        }

        profile_item_favorite_product.setOnClickListener {
            Intent(
                this@UserProfileActivity,
                FavoriteProductIndexActivity::class.java
            ).also { intent ->
                startActivity(intent)
            }
        }
    }
}