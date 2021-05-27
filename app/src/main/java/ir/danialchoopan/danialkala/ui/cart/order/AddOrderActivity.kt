package ir.danialchoopan.danialkala.ui.cart.order

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.spinner.UserAddressSpinnerAdapter
import ir.danialchoopan.danialkala.data.api.volleyRequestes.UserAddressVolleyRequest
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import ir.danialchoopan.danialkala.ui.profile.item.userAddress.UserAddressIndexActivity
import kotlinx.android.synthetic.main.activity_add_order.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class AddOrderActivity : AppCompatActivity() {
    var valid_address = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_order)
        //setup toolbar
        toolbar_auth_title.text = "ادامه فرآیند خرید"
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        //user address
        val loadingProcessDialog = LoadingProcessDialog(this@AddOrderActivity).create()
        loadingProcessDialog.show()
        val addressVolleyRequest = UserAddressVolleyRequest(this@AddOrderActivity)
        addressVolleyRequest.readUserAddress { success, userAddress ->
            loadingProcessDialog.dismiss()
            if (success) {
                if (userAddress.size == 0) {
                    valid_address = false
                    order_tv_message_address.text =
                        "آدرسی جهت نمایش وجود ندارد لطفا ابتدا آدرسی اضافه کنید. \n از قسمت آدرس ها در پروفایل کاربر می توانید آدرس خود را اضافه کنید ."
                    order_spinner_address.visibility = View.INVISIBLE
                    order_btn_add_order.visibility = View.GONE
                    order_open_address.visibility = View.VISIBLE
                } else {
                    valid_address = true


                    order_open_address.visibility = View.GONE
                    order_btn_add_order.visibility = View.VISIBLE
                    order_spinner_address.visibility = View.VISIBLE
                }
            }
        }
        loadingProcessDialog.show()
        val userAddressVolleyRequest = UserAddressVolleyRequest(this@AddOrderActivity)
        userAddressVolleyRequest.readUserAddress { success, userAddress ->
            loadingProcessDialog.dismiss()
            if (success) {
                order_spinner_address.adapter =
                    UserAddressSpinnerAdapter(this@AddOrderActivity, userAddress)
            }
        }

        order_btn_add_order.setOnClickListener {
            if (valid_address) {
                Toast.makeText(
                    this@AddOrderActivity,
                    order_spinner_address.selectedItem.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        order_open_address.setOnClickListener {
            Intent(this@AddOrderActivity, UserAddressIndexActivity::class.java).also { intent ->
                startActivity(intent)
                finish()
            }
        }

    }
}