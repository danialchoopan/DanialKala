package ir.danialchoopan.danialkala.adapter.recyclerView.favoriteProduct

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.volleyRequestes.favoriteProduct.FavoriteProductVolleyRequest
import ir.danialchoopan.danialkala.data.model.requests.favoriteProduct.FavoriteProducts
import ir.danialchoopan.danialkala.data.model.requests.favoriteProduct.FavoriteProductsItem
import ir.danialchoopan.danialkala.ui.product.ShowProductActivity
import kotlinx.android.synthetic.main.row_item_product_category_recyclerview_adapter.view.*
import kotlinx.android.synthetic.main.row_item_recycler_view_favorite_product.view.*

class FavoriteProductRecyclerViewAdapter(
    val m_context: Context,
    val requestDelete: () -> Unit
) :
    RecyclerView.Adapter<FavoriteProductRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(val view: View) :
        RecyclerView.ViewHolder(view)

    var ar_data = emptyList<FavoriteProductsItem>()

    fun setData(ar_data: List<FavoriteProductsItem>) {
        this.ar_data = ar_data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_item_recycler_view_favorite_product, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favoriteProduct = ar_data[position]
        holder.view.row_item_favorite_tv_title_name.text = favoriteProduct.name
        Picasso.get().load(EndPoints.storageImg + favoriteProduct.thumbnail)
            .into(holder.view.row_item_favorite_img_product)
        //show product
        holder.view.setOnClickListener {
            Intent(m_context, ShowProductActivity::class.java).also { intent ->
                intent.putExtra("productId", favoriteProduct.id)
                m_context.startActivity(intent)
            }
        }
        //delete
        holder.view.row_item_favorite_img_delete.setOnClickListener {
            val favoriteProductVolleyRequest = FavoriteProductVolleyRequest(m_context)
            favoriteProductVolleyRequest.likeProduct(favoriteProduct.id.toString()) {

            }
            requestDelete()
        }
    }

    override fun getItemCount(): Int = ar_data.size


}