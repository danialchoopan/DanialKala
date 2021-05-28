package ir.danialchoopan.danialkala.ui.profile.item.userOrders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.recyclerView.orders.OrdersRecyclerView
import ir.danialchoopan.danialkala.adapter.recyclerView.orders.ProductOrdersRecyclerView
import ir.danialchoopan.danialkala.data.api.volleyRequestes.order.OrderVolleyRequest
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import kotlinx.android.synthetic.main.activity_user_order_productctivity.*
import kotlinx.android.synthetic.main.activity_user_orders.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class UserOrderProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_order_productctivity)
        //get intent
        val order_id = intent.extras!!.getString("intentOrderNumber")
        //setup toolbar
        toolbar_auth_title.text = "محصولات سفارش"
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        //setup recycler view
        val ordersRecyclerView = ProductOrdersRecyclerView(this@UserOrderProductActivity)
        order_product_recyclerView.layoutManager =
            LinearLayoutManager(this@UserOrderProductActivity)
        order_product_recyclerView.adapter = ordersRecyclerView
        //get data from api
        val orderVolleyRequest = OrderVolleyRequest(this@UserOrderProductActivity)
        val loadingProcessDialog = LoadingProcessDialog(this).create()
        loadingProcessDialog.show()
        orderVolleyRequest.getUserOrdersProducts(order_id.toString()) { success, orders ->
            loadingProcessDialog.dismiss()
            if (success) {
                ordersRecyclerView.setData(orders)
            }
            if (orders.size == 0) {
                product_order_empty.visibility = View.VISIBLE
            } else {
                product_order_empty.visibility = View.GONE
            }
        }
    }
}