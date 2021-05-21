package ir.danialchoopan.danialkala.adapter.cart

import android.app.AlertDialog
import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.volleyRequestes.cart.CartVolleyRequest
import ir.danialchoopan.danialkala.data.model.requests.userCart.CartDataModel
import ir.danialchoopan.danialkala.data.model.requests.userCart.CartDataModelItem
import ir.danialchoopan.danialkala.utails.FormatNumbers
import kotlinx.android.synthetic.main.row_home_categories.view.*
import kotlinx.android.synthetic.main.row_item_cart_recycler_view.view.*

class CartRecyclerViewAdapter(private val m_context: Context) :
    RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    var ar_data = emptyList<CartDataModelItem>()
    fun setData(ar_data: List<CartDataModelItem>) {
        this.ar_data = ar_data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_item_cart_recycler_view, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productItem = ar_data[position]
        holder.view.row_cart_product_name.text = productItem.name
        holder.view.row_cart_product_price.text =
            FormatNumbers.formatPrice(productItem.price) + " تومان "
        Picasso.get().load(EndPoints.storageImg + productItem.thumbnail)
            .into(holder.view.row_cart_product_img)

        //delete form cart
        holder.view.row_cart_product_img_delete.setOnClickListener {
            AlertDialog.Builder(m_context).also { builder ->
                builder.setTitle("حذف ایتم")
                builder.setMessage("آیا می خواهید این محصول را از سید خرید خود حذف کنید")
                builder.setPositiveButton("بله") { _, _ ->
                    Log.i("tag", "cart id : ${productItem.cart.id}")
                    CartVolleyRequest(m_context).deleteCart(productItem.cart.id.toString()) { success ->
                        if (success) {
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, ar_data.size-1)
                        }
                    }
                }
                builder.setNegativeButton("نه شوخی کردم") { _, _ ->
                }
            }.show()
        }
    }

    override fun getItemCount(): Int = ar_data.size
}