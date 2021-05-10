package ir.danialchoopan.danialkala.ui.showCategory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.recyclerView.ShowProductCardRecyclerViewAdapter
import ir.danialchoopan.danialkala.data.api.volleyRequestes.ShowCategoryVolleyRequest
import kotlinx.android.synthetic.main.activity_show_category.*

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

        //setup back
        product_category_back_arrow.setOnClickListener {
            finish()
        }

        //send the request
        ShowCategoryVolleyRequest(this@ShowCategoryActivity)
            .sendRequest(category_id.toString()) { showCategoryRequestDataModel ->

                val category_item = showCategoryRequestDataModel.category
                //set category name
                product_category_tv_name_category.text = category_item.name

                //setup category products
                showProductCardRecyclerViewAdapter.setData(category_item.products)
            }
    }
}