package ir.danialchoopan.danialkala.ui.product

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.slider.ImgSliderProductViewPagerAdapter
import ir.danialchoopan.danialkala.adapter.slider.ImgSliderProductViewPagerAdapterForProducts
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.data.api.volleyRequestes.ShowProductReviewVolleyRequest
import ir.danialchoopan.danialkala.data.api.volleyRequestes.auth.AuthUserVolleyRequest
import ir.danialchoopan.danialkala.data.api.volleyRequestes.cart.CartVolleyRequest
import ir.danialchoopan.danialkala.data.api.volleyRequestes.favoriteProduct.FavoriteProductVolleyRequest
import ir.danialchoopan.danialkala.data.api.volleyRequestes.product.ShowSingleProductVolleyRequest
import ir.danialchoopan.danialkala.data.model.requests.home.New_products
import ir.danialchoopan.danialkala.data.model.requests.showCategory.Products
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import ir.danialchoopan.danialkala.ui.cart.UserCartActivity
import ir.danialchoopan.danialkala.ui.showProductsProperties.ShowProductsPropertiesActivity
import ir.danialchoopan.danialkala.utails.FormatNumbers
import kotlinx.android.synthetic.main.activity_main.*
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
        val productId = intent.extras!!.getInt("productId")
        //show loading dialog
        val loadingDialogShowProduct = LoadingProcessDialog(this@ShowProductActivity).create()
        loadingDialogShowProduct.show()
        val showSingleProductVolleyRequest =
            ShowSingleProductVolleyRequest(this@ShowProductActivity)
        showSingleProductVolleyRequest.getProductById(productId.toString()) { success, productItem ->
            loadingDialogShowProduct.dismiss()
            if (success) {
                //start if

                val authUserVolleyRequest = AuthUserVolleyRequest(this@ShowProductActivity)
                authUserVolleyRequest.checkToken { successToken ->
                    if (!successToken) {
                        UserSharePreferences(this@ShowProductActivity).sharePreferences.edit()
                            .clear().apply()
                        show_product_cart_layout.visibility = View.GONE
                    } else {
                        show_product_cart_layout.visibility = View.VISIBLE
                    }
                }//end check token
                val cartVolleyRequest = CartVolleyRequest(this@ShowProductActivity)
                cartVolleyRequest.checkIfProductInUserCart(productId.toString()) { successCart ->
                    if (successCart) {
                        show_product_card_added.visibility = View.VISIBLE
                    } else {
                        show_product_card_added.visibility = View.GONE
                    }
                }
                cartVolleyRequest.userCart { successCart, userCart ->
                    if (successCart) {
                        show_product_open_cart_tv_count.text = userCart.size.toString()
                    }
                }
                show_p_lbl_product_price.text =
                    FormatNumbers.formatPrice(productItem.price) + " تومان "
                show_product_favorite_img_progress_bar.visibility = View.VISIBLE
                show_product_favorite_img.visibility = View.GONE
                favoriteProductVolleyRequest.checkIfProductLiked(productItem.id.toString()) { favorite ->
                    Log.i("favorite prod", favorite.toString())
                    show_product_favorite_img_progress_bar.visibility = View.GONE
                    show_product_favorite_img.visibility = View.VISIBLE
                    if (favorite) {
                        show_product_favorite_img.setImageResource(R.drawable.ic_baseline_favorite_24_red)
                    } else {
                        show_product_favorite_img.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    }
                }
                show_product_favorite_img.setOnClickListener {
                    show_product_favorite_img_progress_bar.visibility = View.VISIBLE
                    show_product_favorite_img.visibility = View.GONE
                    favoriteProductVolleyRequest.likeProduct(productItem.id.toString()) {

                        favoriteProductVolleyRequest.checkIfProductLiked(productItem.id.toString()) { favorite ->
                            Log.i("favorite send", favorite.toString())
                            show_product_favorite_img_progress_bar.visibility = View.GONE
                            show_product_favorite_img.visibility = View.VISIBLE
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

                //and to product
                show_product_add_cart.setOnClickListener {
                    show_product_add_cart_progressBar.visibility = View.VISIBLE
                    show_product_add_cart.visibility = View.GONE
                    cartVolleyRequest.addToCart(productId.toString()) { successCart ->
                        show_product_add_cart_progressBar.visibility = View.GONE
                        show_product_add_cart.visibility = View.VISIBLE
                        if (successCart) {
                            Toast.makeText(
                                this@ShowProductActivity,
                                "محصول با موفقیت به سبد خرید شما اضافه شد",
                                Toast.LENGTH_SHORT
                            ).show()
                            show_product_card_added.visibility = View.VISIBLE

                            cartVolleyRequest.userCart { successCartCount, userCart ->
                                if (successCartCount) {
                                    show_product_open_cart_tv_count.text = userCart.size.toString()
                                }
                            }

                        } else {
                            Toast.makeText(
                                this@ShowProductActivity,
                                "مشکلی پش آمده است لطفا دوباره امتحان کنید",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } //end if start else

                show_product_cart_layout.setOnClickListener {
                    Intent(this@ShowProductActivity, UserCartActivity::class.java).also { intent ->
                        startActivity(intent)
                    }
                }
            } else {
                Toast.makeText(
                    this@ShowProductActivity,
                    "مشکلی پش آمده است لطفا دوباره امتحان کنید",
                    Toast.LENGTH_SHORT
                ).show()
                //finish
                finish()
            }
        }
    }
}