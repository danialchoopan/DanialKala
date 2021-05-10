package ir.danialchoopan.danialkala.adapter.viewpager

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.recyclerView.ProductCategoryRecyclerViewAdapter
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.model.requests.category.Category_Base
import kotlinx.android.synthetic.main.row_img_slider_item.view.*
import kotlinx.android.synthetic.main.row_product_category_recycler_view.view.*

class ProductCategoryViewPagerAdapter(val m_context: Context, val ar_data: List<Category_Base>) :
    PagerAdapter() {
    override fun getCount(): Int = ar_data.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (view == `object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(m_context)
            .inflate(R.layout.row_product_category_recycler_view, null)
        view.row_product_category_recycler_view_id.layoutManager =
            LinearLayoutManager(m_context)
        val productCategoryRecyclerViewAdapter = ProductCategoryRecyclerViewAdapter(m_context)
        view.row_product_category_recycler_view_id.adapter = productCategoryRecyclerViewAdapter
        productCategoryRecyclerViewAdapter.setData(ar_data[position].sub_categories)
        container.addView(view)
        return view
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return ar_data[position].name
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}