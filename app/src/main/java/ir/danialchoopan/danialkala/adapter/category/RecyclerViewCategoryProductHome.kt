package ir.danialchoopan.danialkala.adapter.category

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.model.ProductCategories
import ir.danialchoopan.danialkala.data.model.requests.home.Categories
import kotlinx.android.synthetic.main.row_home_categories.view.*
import kotlinx.android.synthetic.main.row_product_home.view.*

class RecyclerViewCategoryProductHome :
    RecyclerView.Adapter<RecyclerViewCategoryProductHome.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    var listData = emptyList<Categories>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_home_categories, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productCategoryItem = listData[position]

        holder.view.row_lblCategoryName.text = productCategoryItem.name

        //load img
        Picasso.get().load(EndPoints.storage + productCategoryItem.category_photo)
            .into(holder.view.row_imgCategory)
        Log.i("categories product", "${EndPoints.storage + productCategoryItem.category_photo}")

    }

    override fun getItemCount(): Int = listData.size


    fun setData(list: List<Categories>) {
        listData = list
        notifyDataSetChanged()
    }

}