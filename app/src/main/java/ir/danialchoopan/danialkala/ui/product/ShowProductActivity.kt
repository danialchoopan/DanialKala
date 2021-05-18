package ir.danialchoopan.danialkala.ui.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.slider.ImgSliderProductViewPagerAdapter
import ir.danialchoopan.danialkala.adapter.slider.ImgSliderProductViewPagerAdapterForProducts
import ir.danialchoopan.danialkala.data.api.volleyRequestes.ShowProductReviewVolleyRequest
import ir.danialchoopan.danialkala.data.api.volleyRequestes.favoriteProduct.FavoriteProductVolleyRequest
import ir.danialchoopan.danialkala.data.model.requests.home.New_products
import ir.danialchoopan.danialkala.data.model.requests.showCategory.Products
import ir.danialchoopan.danialkala.ui.showProductsProperties.ShowProductsPropertiesActivity
import kotlinx.android.synthetic.main.activity_show_product.*

class ShowProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_product)
        //back btn
        product_show_btn_back.setOnClickListener {
            finish()
        }
        val favoriteProductVolleyRequest = FavoriteProductVolleyRequest(this@ShowProductActivity)
        //get product item
        try {
            val productItem = intent.extras!!.get("productItem") as New_products

            show_product_favorite_img_progress_bar.visibility=View.VISIBLE
            show_product_favorite_img.visibility=View.GONE
            favoriteProductVolleyRequest.checkIfProductLiked(productItem.id.toString()) { favorite ->
                Log.i("favorite prod",favorite.toString())
                show_product_favorite_img_progress_bar.visibility=View.GONE
                show_product_favorite_img.visibility=View.VISIBLE
                if (favorite) {
                    show_product_favorite_img.setImageResource(R.drawable.ic_baseline_favorite_24_red)
                } else {
                    show_product_favorite_img.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
            }
            show_product_favorite_img.setOnClickListener {
                show_product_favorite_img_progress_bar.visibility=View.VISIBLE
                show_product_favorite_img.visibility=View.GONE
                favoriteProductVolleyRequest.likeProduct(productItem.id.toString()){

                    favoriteProductVolleyRequest.checkIfProductLiked(productItem.id.toString()) { favorite ->
                        Log.i("favorite send",favorite.toString())
                        show_product_favorite_img_progress_bar.visibility=View.GONE
                        show_product_favorite_img.visibility=View.VISIBLE
                        if (favorite) {
                            show_product_favorite_img.setImageResource(R.drawable.ic_baseline_favorite_24_red)
                        } else {
                            show_product_favorite_img.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                        }
                    }

                }
            }
            //set adapter slider
            val productImgSliderAdapter =
                ImgSliderProductViewPagerAdapter(
                    this@ShowProductActivity,
                    productItem.productphotos
                )
            show_product_slider_img_product.adapter = productImgSliderAdapter

            //set product name
            show_p_lbl_product_name.text = productItem.name
            //scroll view
            show_product_scrollView.viewTreeObserver.addOnScrollChangedListener {
                val db_scroll_y = show_product_scrollView.scrollY
                if (db_scroll_y > 500) {
                    product_show_lbl_title_toolbar_product.text = productItem.name
                } else {
                    product_show_lbl_title_toolbar_product.text = ""
                }
            }


            //show properties
            show_product_card_properties.setOnClickListener {
                Intent(
                    this@ShowProductActivity,
                    ShowProductsPropertiesActivity::class.java
                ).also { intent ->
                    intent.putExtra("productId", productItem.id.toString())
                    startActivity(intent)
                }
            }

            show_product_tv_description.text = productItem.description

            //setup product review
            val showProductReviewVolleyRequest =
                ShowProductReviewVolleyRequest(this@ShowProductActivity)
            showProductReviewVolleyRequest.sendRequest(productItem.id.toString()) { success, productReview ->
                if (success && productReview.review.isNotEmpty()) {
                    show_product_linear_review_box.visibility = View.VISIBLE
                    show_product_tv_empty_review.visibility = View.GONE
                    show_product_tv_show_review.text = productReview.review
                } else {
                    show_product_linear_review_box.visibility = View.GONE
                    show_product_linear_review_box.visibility = View.VISIBLE
                }

                if (productReview.review.isEmpty()) {
                    show_product_linear_review_box.visibility = View.GONE
                }
            }

        } catch (e: Exception) {
            val productItem = intent.extras!!.get("productItem") as Products

            //set adapter slider
            val productImgSliderAdapter =
                ImgSliderProductViewPagerAdapterForProducts(
                    this@ShowProductActivity,
                    productItem.productphotos
                )
            show_product_slider_img_product.adapter = productImgSliderAdapter

            //set product name
            show_p_lbl_product_name.text = productItem.name
            //scroll view
            show_product_scrollView.viewTreeObserver.addOnScrollChangedListener {
                val db_scroll_y = show_product_scrollView.scrollY
                if (db_scroll_y > 500) {
                    product_show_lbl_title_toolbar_product.text = productItem.name
                } else {
                    product_show_lbl_title_toolbar_product.text = ""
                }
            }


            //show properties
            show_product_card_properties.setOnClickListener {
                Intent(
                    this@ShowProductActivity,
                    ShowProductsPropertiesActivity::class.java
                ).also { intent ->
                    intent.putExtra("productId", productItem.id.toString())
                    startActivity(intent)
                }
            }

            show_product_tv_description.text = productItem.description

            //setup product review
            val showProductReviewVolleyRequest =
                ShowProductReviewVolleyRequest(this@ShowProductActivity)
            showProductReviewVolleyRequest.sendRequest(productItem.id.toString()) { success, productReview ->
                if (success && productReview.review.isNotEmpty()) {
                    show_product_linear_review_box.visibility = View.VISIBLE
                    show_product_tv_empty_review.visibility = View.GONE
                    show_product_tv_show_review.text = productReview.review
                } else {
                    show_product_linear_review_box.visibility = View.GONE
                    show_product_linear_review_box.visibility = View.VISIBLE
                }

                if (productReview.review.isEmpty()) {
                    show_product_linear_review_box.visibility = View.GONE
                }
            }

        }//end catch
    }
}