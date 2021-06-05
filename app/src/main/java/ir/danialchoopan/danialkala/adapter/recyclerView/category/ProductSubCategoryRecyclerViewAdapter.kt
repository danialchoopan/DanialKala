package ir.danialchoopan.danialkala.adapter.recyclerView.category

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.model.requests.subCategory.SubCategoryItem
import ir.danialchoopan.danialkala.ui.showCategory.ShowCategoryActivity
import kotlinx.android.synthetic.main.row_item_product_category_recyclerview_adapter.view.*

class ProductSubCategoryRecyclerViewAdapter(val m_context: Context) :
    RecyclerView.Adapter<ProductSubCategoryRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    var ar_data = emptyList<SubCategoryItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_product_category_recyclerview_adapter, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category_item = ar_data[position]
        holder.view.lbl_name_row_category_product.text = category_item.name
        Picasso.get().load(EndPoints.storage + category_item.photo.path)
            .placeholder(R.drawable.pholder)
            .into(holder.view.imgView_item_category_product)

        holder.view.setOnClickListener {
            Intent(m_context, ShowCategoryActivity::class.java).also { intent ->
                intent.putExtra("category_item_id", category_item.id)
                m_context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = ar_data.size

    fun setData(ar_data: List<SubCategoryItem>) {
        this.ar_data = ar_data
        notifyDataSetChanged()
    }

}