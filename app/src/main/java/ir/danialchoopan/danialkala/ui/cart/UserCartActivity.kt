package ir.danialchoopan.danialkala.ui.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.cart.CartRecyclerViewAdapter
import ir.danialchoopan.danialkala.data.api.volleyRequestes.auth.AuthUserVolleyRequest
import ir.danialchoopan.danialkala.data.api.volleyRequestes.cart.CartVolleyRequest
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import ir.danialchoopan.danialkala.ui.cart.order.AddOrderActivity
import ir.danialchoopan.danialkala.utails.FormatNumbers
import kotlinx.android.synthetic.main.activity_user_cart.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class UserCartActivity : AppCompatActivity() {
    lateinit var cartVolleyRequest: CartVolleyRequest
    lateinit var cartRecyclerViewAdapter: CartRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_cart)
        //setup toolbar
        toolbar_auth_title.text = "سبد خرید"
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        //setup recycler view
        cartRecyclerViewAdapter = CartRecyclerViewAdapter(this@UserCartActivity) {
            getUserCart()
        }
        cart_rcy_show_products_cart.layoutManager = LinearLayoutManager(this@UserCartActivity)
        cart_rcy_show_products_cart.adapter = cartRecyclerViewAdapter

        //get data form api
        cartVolleyRequest = CartVolleyRequest(this@UserCartActivity)

        //open add order
        cart_btn_checkout.setOnClickListener {
            val loadingProcessDialogCheckout = LoadingProcessDialog(this@UserCartActivity).create()
            loadingProcessDialogCheckout.show()
            val authUserVolleyRequest = AuthUserVolleyRequest(this@UserCartActivity)
            authUserVolleyRequest.getUserData { editProfileDataModelRequest ->
                loadingProcessDialogCheckout.dismiss()
                if (editProfileDataModelRequest.success) {
                    if (editProfileDataModelRequest.user.email_verified_at == null) {
                        Toast.makeText(
                            this@UserCartActivity,
                            "برای ثبت سفارش باید پست الکترونیک خود را تایید کنید . برای تایید پست الکترونیک خود پروفایل را برسی کنید",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Intent(this@UserCartActivity, AddOrderActivity::class.java).also { intent ->
                            startActivity(intent)
                        }
                    }//else
                }//end if
            }//end get user data
        }//end btn
    }//end onCreate

    override fun onResume() {
        super.onResume()
        getUserCart()
    }

    private fun getUserCart() {
        val loadingDialogUserCard = LoadingProcessDialog(this@UserCartActivity).create()
        loadingDialogUserCard.show()
        cartVolleyRequest.userCart { success, userCart ->
            loadingDialogUserCard.dismiss()
            if (success) {
                if (userCart.size == 0) {
                    cart_tv_empty.visibility = View.VISIBLE
                } else {
                    cart_tv_empty.visibility = View.GONE
                }
                var sum_price = 0
                userCart.forEach { item ->
                    sum_price += item.price.toInt()
                }
                cart_tv_sum_price.text = "${FormatNumbers.formatPrice(sum_price.toString())} تومان "
                cartRecyclerViewAdapter.setData(userCart)
            } else {
                Toast.makeText(
                    this@UserCartActivity,
                    "مشکلی پیش آمده است لطفا دوباره امتحان کنید",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }//end get user cart
    }
}