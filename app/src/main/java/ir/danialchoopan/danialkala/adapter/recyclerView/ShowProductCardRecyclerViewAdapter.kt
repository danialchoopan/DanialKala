package ir.danialchoopan.danialkala.adapter.recyclerView

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.ui.product.ShowProductActivity
import ir.danialchoopan.danialkala.data.api.EndPoints
import kotlinx.android.synthetic.main.row_product_home.view.*

import ir.danialchoopan.danialkala.data.model.requests.home.New_products
import ir.danialchoopan.danialkala.data.model.requests.showCategory.Products
import ir.danialchoopan.danialkala.utails.FormatNumbers

class ShowProductCardRecyclerViewAdapter(val context: Context) :
    RecyclerView.Adapter<ShowProductCardRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    var listData = emptyList<Products>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_product_home, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productItem = listData[position]
        holder.view.row_lblProductName.text = productItem.name
        holder.view.row_lblProductCategory.text = productItem.category
        holder.view.row_lblProductPrice.text = FormatNumbers.formatPrice(productItem.price.toString()) + "تومان"
        //load img
        Picasso.get().load(EndPoints.storageImg + productItem.thumbnail)
            .into(holder.view.row_imgProduct)

        holder.view.setOnClickListener {
            Intent(context, ShowProductActivity::class.java).also { intent ->
                intent.putExtra("productId", productItem.id)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = listData.size

    fun setData(list: List<Products>) {
        listData = list
        notifyDataSetChanged()
    }


}