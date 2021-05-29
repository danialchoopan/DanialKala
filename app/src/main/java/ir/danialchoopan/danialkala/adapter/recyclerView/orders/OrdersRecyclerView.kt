package ir.danialchoopan.danialkala.adapter.recyclerView.orders

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.model.requests.orders.OrderDataModelItem
import ir.danialchoopan.danialkala.ui.profile.item.userOrders.UserOrderProductActivity
import ir.danialchoopan.danialkala.utails.FormatNumbers
import kotlinx.android.synthetic.main.row_orders_recycler_view_item.view.*

class OrdersRecyclerView(private val m_context: Context) :
    RecyclerView.Adapter<OrdersRecyclerView.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    private var ar_data = emptyList<OrderDataModelItem>()
    fun setData(ar_data: List<OrderDataModelItem>) {
        this.ar_data = ar_data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_orders_recycler_view_item, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderItem = ar_data[position]
        holder.view.item_orders_order_number.text = "شماره سفارش : " + orderItem.order_product_id
        holder.view.item_orders_order_description.text = "توضیحات : " + orderItem.description
        holder.view.item_orders_order_price.text =
            FormatNumbers.formatPrice(orderItem.price.toString()) + "تومان"

        if (orderItem.status == 100) {
            holder.view.item_orders_order_status_success.visibility = View.VISIBLE
            holder.view.item_orders_order_status_error.visibility = View.GONE
        } else {
            holder.view.item_orders_order_status_success.visibility = View.GONE
            holder.view.item_orders_order_status_error.visibility = View.VISIBLE
        }

        //open product order
        holder.view.setOnClickListener {
            Intent(m_context, UserOrderProductActivity::class.java).also { intent ->
                intent.putExtra("intentOrderNumber", orderItem.order_product_id.toString())
                m_context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = ar_data.size
}