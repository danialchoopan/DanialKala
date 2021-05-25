package ir.danialchoopan.danialkala.ui.showCategory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.recyclerView.ShowProductCardRecyclerViewAdapter
import ir.danialchoopan.danialkala.data.api.volleyRequestes.ShowCategoryVolleyRequest
import kotlinx.android.synthetic.main.activity_show_category.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class ShowCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_category)
        val category_id = intent.extras!!.getInt("category_item_id")
        //setup recycler view
        val showProductCardRecyclerViewAdapter =
            ShowProductCardRecyclerViewAdapter(this@ShowCategoryActivity)
        rcy_show_category_products.layoutManager = GridLayoutManager(this@ShowCategoryActivity, 2)
        rcy_show_category_products.adapter = showProductCardRecyclerViewAdapter
        //setup toolbar
        toolbar_auth_title.text = "نمایش دسته بندی این محصول"
        toolbar_auth_close.setOnClickListener {
            finish()
        }

        //send the request
        ShowCategoryVolleyRequest(this@ShowCategoryActivity)
            .sendRequest(category_id.toString()) { showCategoryRequestDataModel ->
                if (showCategoryRequestDataModel.category.products.isEmpty()) {
                    pc_empty.visibility = View.VISIBLE
                } else {
                    pc_empty.visibility = View.GONE
                }
                val category_item = showCategoryRequestDataModel.category
                //set category name
                product_category_tv_name_category.text = category_item.name

                //setup category products
                showProductCardRecyclerViewAdapter.setData(category_item.products)
            }
    }
}