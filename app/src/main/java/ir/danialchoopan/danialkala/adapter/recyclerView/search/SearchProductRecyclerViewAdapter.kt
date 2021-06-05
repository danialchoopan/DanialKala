package ir.danialchoopan.danialkala.adapter.recyclerView.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.model.requests.searchProduct.SearchProductDataModelItem
import ir.danialchoopan.danialkala.ui.product.ShowProductActivity
import ir.danialchoopan.danialkala.utails.FormatNumbers
import kotlinx.android.synthetic.main.row_item_product_category_recyclerview_adapter.view.*
import kotlinx.android.synthetic.main.row_item_search_product_reycler_view.view.*

class SearchProductRecyclerViewAdapter(private val m_context: Context) :
    RecyclerView.Adapter<SearchProductRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    private var ar_data = emptyList<SearchProductDataModelItem>()

    fun setData(ar_data: List<SearchProductDataModelItem>) {
        this.ar_data = ar_data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_item_search_product_reycler_view, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productItem = ar_data[position]
        holder.view.row_item_search_product_name.text = productItem.name

        holder.view.row_item_search_product_price.text =
            FormatNumbers.formatPrice(productItem.price) + " تومان "

        Picasso.get().load(EndPoints.storageImg + productItem.thumbnail)
            .placeholder(R.drawable.pholder)
            .into(holder.view.row_item_search_product_img)

        //go to product show
        holder.view.setOnClickListener {
            Intent(m_context, ShowProductActivity::class.java).also { intent ->
                intent.putExtra("productId", productItem.id)
                m_context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = ar_data.size
}