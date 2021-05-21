package ir.danialchoopan.danialkala.ui.subCategory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.recyclerView.ProductCategoryRecyclerViewAdapter
import ir.danialchoopan.danialkala.adapter.recyclerView.category.ProductSubCategoryRecyclerViewAdapter
import ir.danialchoopan.danialkala.adapter.viewpager.ProductCategoryViewPagerAdapter
import ir.danialchoopan.danialkala.data.api.volleyRequestes.category.CategoryVolleyRequest
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import ir.danialchoopan.danialkala.ui.category.ProductCategoryViewModel
import kotlinx.android.synthetic.main.activity_product_category.*
import kotlinx.android.synthetic.main.activity_show_sub_category.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class ShowSubCategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_sub_category)

        //setup toolbar
        toolbar_auth_title.text = "دسته بندی محصولات"
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        //get intent
        val categoryId = intent.extras!!.getString("categoryId")

        //setup recycler view
        val productSubCategoryRecyclerViewAdapter =
            ProductSubCategoryRecyclerViewAdapter(this@ShowSubCategoryActivity)
        rcy_sub_category.layoutManager = LinearLayoutManager(this@ShowSubCategoryActivity)
        rcy_sub_category.adapter = productSubCategoryRecyclerViewAdapter

        //get data form api
        val loadingDialogSubCategory = LoadingProcessDialog(this@ShowSubCategoryActivity).create()
        loadingDialogSubCategory.show()
        val categoryVolleyRequest = CategoryVolleyRequest(this@ShowSubCategoryActivity)
        categoryVolleyRequest.getSubCategory(categoryId.toString()) { success, subCategory ->
            loadingDialogSubCategory.dismiss()
            if (success) {
                if (subCategory.isEmpty()) {
                    category_tv_empty.visibility = View.VISIBLE
                } else {
                    category_tv_empty.visibility = View.GONE
                    productSubCategoryRecyclerViewAdapter.setData(subCategory)
                }
            } else {
                Toast.makeText(
                    this@ShowSubCategoryActivity,
                    "مشکلی پیش آمده است لطفا بعدا امتحان کنید",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }//end category volley request
    }
}