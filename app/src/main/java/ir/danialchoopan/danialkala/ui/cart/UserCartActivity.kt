package ir.danialchoopan.danialkala.ui.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.cart.CartRecyclerViewAdapter
import ir.danialchoopan.danialkala.data.api.volleyRequestes.cart.CartVolleyRequest
import ir.danialchoopan.danialkala.utails.FormatNumbers
import kotlinx.android.synthetic.main.activity_user_cart.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class UserCartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_cart)
        //setup toolbar
        toolbar_auth_title.text = "سبد خرید"
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        //setup recycler view
        val cartRecyclerViewAdapter = CartRecyclerViewAdapter(this@UserCartActivity)
        cart_rcy_show_products_cart.layoutManager = LinearLayoutManager(this@UserCartActivity)
        cart_rcy_show_products_cart.adapter = cartRecyclerViewAdapter

        //get data form api
        val cartVolleyRequest = CartVolleyRequest(this@UserCartActivity)
        cartVolleyRequest.userCart { success, userCart ->
            if (success) {
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

        }
    }
}