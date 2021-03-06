package ir.danialchoopan.danialkala.ui.profile.item.userAddress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.recyclerView.userAddress.UserAddressRecyclerAdapter
import ir.danialchoopan.danialkala.data.api.volleyRequestes.UserAddressVolleyRequest
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import kotlinx.android.synthetic.main.activity_user_address_index.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class UserAddressIndexActivity : AppCompatActivity() {
    lateinit var userAddressRecyclerAdapter: UserAddressRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_address_index)
        //setup toolbar
        toolbar_auth_title.text = "آدرس ها"
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        //setup recycler view
        rcy_userAddress.layoutManager = LinearLayoutManager(this@UserAddressIndexActivity)
        userAddressRecyclerAdapter = UserAddressRecyclerAdapter(this@UserAddressIndexActivity) {
            readApiUserAddress()
        }
        rcy_userAddress.adapter = userAddressRecyclerAdapter
        //fbtn for add user address activity
        fbtn_AddAddress.setOnClickListener {
            Intent(
                this@UserAddressIndexActivity,
                UserAddressAddActivity::class.java
            ).also { intent ->
                startActivity(intent)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        readApiUserAddress()
    }


    private fun readApiUserAddress() {
        val userAddressVolleyRequest = UserAddressVolleyRequest(this@UserAddressIndexActivity)
        //loading dialog
        val loadingProcessDialog = LoadingProcessDialog(this).create()
        loadingProcessDialog.show()
        userAddressVolleyRequest.readUserAddress { success, userAddress ->
            if (userAddress.size == 0) {
                user_address_empty_address.visibility = View.VISIBLE
            } else {
                user_address_empty_address.visibility = View.GONE
            }
            loadingProcessDialog.dismiss()
            if (success) {
                userAddressRecyclerAdapter.setData(userAddress)
            } else {
                Toast.makeText(
                    this@UserAddressIndexActivity,
                    "مشکلی پس آمده است لطفا بعدا دوباره امتحان کنید",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}