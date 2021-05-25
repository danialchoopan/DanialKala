package ir.danialchoopan.danialkala.ui.showAllProduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.recyclerView.moreProduct.ShowProductAllRecyclerViewAdapter
import ir.danialchoopan.danialkala.data.api.volleyRequestes.product.more.MoreProductVolleyRequest
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import kotlinx.android.synthetic.main.activity_more_product_layout.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class MoreProductLayoutActivity : AppCompatActivity() {
    lateinit var moreProductVolleyRequest: MoreProductVolleyRequest
    lateinit var showProductAllRecyclerViewAdapter: ShowProductAllRecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_product_layout)
        //setup toolbar
        toolbar_auth_title.text = "مشاهده همه محصولات"
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        //setup recycler view
        showProductAllRecyclerViewAdapter =
            ShowProductAllRecyclerViewAdapter(this@MoreProductLayoutActivity)
        more_rcy_more_product.layoutManager = GridLayoutManager(this@MoreProductLayoutActivity, 2)
        more_rcy_more_product.adapter = showProductAllRecyclerViewAdapter

        moreProductVolleyRequest = MoreProductVolleyRequest(this@MoreProductLayoutActivity)
        //get products form api
        getAllProductsFromApi()
    }

    private fun getAllProductsFromApi() {
        val loadingDialogAllProducts = LoadingProcessDialog(this@MoreProductLayoutActivity).create()
        loadingDialogAllProducts.show()
        moreProductVolleyRequest.getAllProducts { success, moreAllProduct ->
            loadingDialogAllProducts.dismiss()
            if (success) {
                showProductAllRecyclerViewAdapter.setData(moreAllProduct)
            } else {
                Toast.makeText(
                    this@MoreProductLayoutActivity,
                    "مشکلی پیش آمده است لطفا بعدا امتحان کنید",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}