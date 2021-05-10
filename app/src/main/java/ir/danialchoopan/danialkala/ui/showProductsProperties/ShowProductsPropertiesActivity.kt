package ir.danialchoopan.danialkala.ui.showProductsProperties

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.recyclerView.ProductPropertiesRecyclerViewAdapter
import ir.danialchoopan.danialkala.data.api.volleyRequestes.ShowProductPropertiesVolleyRequest
import kotlinx.android.synthetic.main.activity_show_products_properties.*

class ShowProductsPropertiesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_products_properties)
        //back arrow toolbar
        product_properties_back_arrow.setOnClickListener {
            finish()
        }

        //get product id form intent
        val productId = intent.extras!!.getString("productId")
        val showProductPropertiesVolleyRequest =
            ShowProductPropertiesVolleyRequest(this@ShowProductsPropertiesActivity)


        //setup recycler view
        val productPropertiesRecyclerViewAdapter =
            ProductPropertiesRecyclerViewAdapter(this@ShowProductsPropertiesActivity)
        rcy_show_product_properties.layoutManager =
            LinearLayoutManager(this@ShowProductsPropertiesActivity)
        rcy_show_product_properties.adapter = productPropertiesRecyclerViewAdapter
        showProductPropertiesVolleyRequest.sendRequest(productId!!) { success, productPropertiesDataModel ->
            if (success) {
                show_product_properties_toolbar_title.text =
                    "مشخصات : " + productPropertiesDataModel.name
                productPropertiesRecyclerViewAdapter.setData(productPropertiesDataModel.properties_products)
                if (productPropertiesDataModel.properties_products.isEmpty()) {
                    tv_msg_empty_rcy_properties.visibility = View.VISIBLE
                }
            } else {
                tv_msg_empty_rcy_properties.visibility = View.VISIBLE
            }
            progressBar_show_product_properties.visibility = View.GONE
        } //end show product properties

    }
}