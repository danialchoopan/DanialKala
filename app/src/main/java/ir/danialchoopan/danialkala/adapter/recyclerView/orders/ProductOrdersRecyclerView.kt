package ir.danialchoopan.danialkala.adapter.recyclerView.orders

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.model.requests.orders.OrderDataModelItem
import ir.danialchoopan.danialkala.data.model.requests.productOrder.ProductOrder
import ir.danialchoopan.danialkala.data.model.requests.productOrder.ProductOrderItem
import ir.danialchoopan.danialkala.ui.product.ShowProductActivity
import ir.danialchoopan.danialkala.utails.FormatNumbers
import kotlinx.android.synthetic.main.row_order_product_recycler_view.view.*
import kotlinx.android.synthetic.main.row_orders_recycler_view_item.view.*
import kotlinx.android.synthetic.main.row_product_home.view.*

class ProductOrdersRecyclerView(private val m_context: Context) :
    RecyclerView.Adapter<ProductOrdersRecyclerView.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    private var ar_data = emptyList<ProductOrderItem>()
    fun setData(ar_data: List<ProductOrderItem>) {
        this.ar_data = ar_data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_order_product_recycler_view, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productOrderItem = ar_data[position]
        holder.view.product_order_title.text = productOrderItem.name
        holder.view.product_order_price.text =
            FormatNumbers.formatPrice(productOrderItem.price.toString()) + "تومان"

        //load img
        Picasso.get().load(EndPoints.storageImg + productOrderItem.thumbnail)
            .placeholder(R.drawable.pholder)
            .into(holder.view.product_order_img)

        //show product
        holder.view.setOnClickListener {
            Intent(m_context, ShowProductActivity::class.java).also { intent ->
                intent.putExtra("productId", productOrderItem.id)
                m_context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int = ar_data.size
}