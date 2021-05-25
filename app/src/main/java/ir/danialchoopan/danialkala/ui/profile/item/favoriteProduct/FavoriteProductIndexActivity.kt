package ir.danialchoopan.danialkala.ui.profile.item.favoriteProduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.recyclerView.favoriteProduct.FavoriteProductRecyclerViewAdapter
import ir.danialchoopan.danialkala.data.api.volleyRequestes.favoriteProduct.FavoriteProductVolleyRequest
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import kotlinx.android.synthetic.main.activity_favorite_product_index.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class FavoriteProductIndexActivity : AppCompatActivity() {
    lateinit var favoriteProductRecyclerViewAdapter: FavoriteProductRecyclerViewAdapter
    lateinit var favoriteProductVolleyRequest: FavoriteProductVolleyRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_product_index)
        toolbar_auth_title.text = "محصولات مورد علاقه"
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        favoriteProductVolleyRequest =
            FavoriteProductVolleyRequest(this@FavoriteProductIndexActivity)

        //setup recycler view
        favoriteProductRecyclerViewAdapter =
            FavoriteProductRecyclerViewAdapter(this@FavoriteProductIndexActivity) {
                getFavoriteProduct()
            }
        rcy_favoriteProduct.layoutManager = LinearLayoutManager(this@FavoriteProductIndexActivity)
        rcy_favoriteProduct.adapter = favoriteProductRecyclerViewAdapter
        getFavoriteProduct()
    }

    private fun getFavoriteProduct() {
        val loadingDialogFavoriteProduct =
            LoadingProcessDialog(this@FavoriteProductIndexActivity).create()
        loadingDialogFavoriteProduct.show()
        favoriteProductVolleyRequest.getAllProducts { success, favoriteProduct ->
            loadingDialogFavoriteProduct.dismiss()
            if (success) {
                favoriteProductRecyclerViewAdapter.setData(favoriteProduct)
                favoriteProduct_nonProduct.visibility = View.GONE
                if (favoriteProduct.size == 0) {
                    favoriteProduct_nonProduct.visibility = View.VISIBLE
                }
            } else {
                favoriteProduct_nonProduct.visibility = View.VISIBLE
            }
        }//end get favorite product
    }
}