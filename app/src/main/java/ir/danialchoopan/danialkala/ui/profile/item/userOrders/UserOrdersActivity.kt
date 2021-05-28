package ir.danialchoopan.danialkala.ui.profile.item.userOrders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.recyclerView.orders.OrdersRecyclerView
import ir.danialchoopan.danialkala.data.api.volleyRequestes.order.OrderVolleyRequest
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import kotlinx.android.synthetic.main.activity_user_orders.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class UserOrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_orders)
        //setup toolbar
        toolbar_auth_title.text = "سفارشات"
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        //setup recycler view
        val ordersRecyclerView = OrdersRecyclerView(this@UserOrdersActivity)
        orders_recycler_view_orders.layoutManager = LinearLayoutManager(this@UserOrdersActivity)
        orders_recycler_view_orders.adapter = ordersRecyclerView
        //get data from api
        val orderVolleyRequest = OrderVolleyRequest(this@UserOrdersActivity)
        val loadingProcessDialog = LoadingProcessDialog(this).create()
        loadingProcessDialog.show()
        orderVolleyRequest.getUserOrders { success, orders ->
            loadingProcessDialog.dismiss()
            if (success) {
                ordersRecyclerView.setData(orders)
            }
            if (orders.size == 0) {
                orders_empty.visibility = View.VISIBLE
            } else {
                orders_empty.visibility = View.GONE
            }
        }
    }
}