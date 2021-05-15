package ir.danialchoopan.danialkala.ui.profile.item

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class ItemEditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_edit_profile)
        val loadingProcessDialog = LoadingProcessDialog(this)
        //toolbar
        toolbar_auth_title.text = "ویرایش اطلاعات کاربری"
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        //end toolbar
        loadingProcessDialog.show()

    }
}