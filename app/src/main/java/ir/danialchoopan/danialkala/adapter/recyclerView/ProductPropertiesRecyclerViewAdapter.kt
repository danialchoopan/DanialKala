package ir.danialchoopan.danialkala.adapter.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.model.requests.showProductPropertise.PropertiesProduct
import ir.danialchoopan.danialkala.data.model.requests.showProductPropertise.ShowProductPropertiesDataModelGson
import kotlinx.android.synthetic.main.item_list_row_product_properties_recycler_view.view.*
import kotlinx.android.synthetic.main.row_product_properties_recycler_view.view.*

class ProductPropertiesRecyclerViewAdapter(val m_context: Context) :
    RecyclerView.Adapter<ProductPropertiesRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    var ar_data = emptyList<PropertiesProduct>()


    fun setData(ar_data: List<PropertiesProduct>) {
        this.ar_data = ar_data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.row_product_properties_recycler_view, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val propertiesItem = ar_data[position]
        holder.view.row_product_properties_tv_name.text = propertiesItem.name
        propertiesItem.sub_properties_product.forEach { subPropertiesProduct ->
            val itemViewSubProperties =
                LayoutInflater.from(m_context)
                    .inflate(R.layout.item_list_row_product_properties_recycler_view, null, false)
            itemViewSubProperties.item_row_product_properties_tv_name.text =
                subPropertiesProduct.name
            itemViewSubProperties.item_row_product_properties_tv_value.text =
                subPropertiesProduct.value
            holder.view.row_product_properties_list_sub_product_properties.addView(
                itemViewSubProperties
            )
        }

    }

    override fun getItemCount(): Int = ar_data.size
}