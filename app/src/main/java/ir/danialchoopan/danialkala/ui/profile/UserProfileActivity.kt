package ir.danialchoopan.danialkala.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.UserSharePreferences
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
            Intent(this@UserProfileActivity, FavoriteProductIndexActivity::class.java).also { intent ->
                startActivity(intent)
            }
        }
    }
}