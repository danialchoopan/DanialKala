package ir.danialchoopan.danialkala.ui.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.viewpager.ProductCategoryViewPagerAdapter
import kotlinx.android.synthetic.main.activity_product_category.*

class ProductCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_category)
        //back btn arrow
        product_category_back_arrow.setOnClickListener {
            finish()
        }

        //set view model
        val viewModelProductCategoryActivity =
            ViewModelProvider(this@ProductCategoryActivity)[ProductCategoryViewModel::class.java]
        viewModelProductCategoryActivity.getProductCategory()
        viewModelProductCategoryActivity.productCategory.observe(this, Observer { categoty_base ->
            product_category_list_category.adapter =
                ProductCategoryViewPagerAdapter(this@ProductCategoryActivity, categoty_base)
            tab_layout_product_category.setupWithViewPager(product_category_list_category)

        })
    }
}