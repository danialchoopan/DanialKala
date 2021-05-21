package ir.danialchoopan.danialkala.ui.product.comment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.comment.ProductCommentRecyclerViewAdapter
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.data.api.volleyRequestes.category.CategoryVolleyRequest
import ir.danialchoopan.danialkala.data.api.volleyRequestes.product.comments.ProductCommentVolleyRequest
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import kotlinx.android.synthetic.main.activity_product_comment.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class ProductCommentActivity : AppCompatActivity() {
    lateinit var productCommentRecyclerViewAdapter: ProductCommentRecyclerViewAdapter
    lateinit var userSharePreferences: UserSharePreferences
    lateinit var productCommentVolleyRequest: ProductCommentVolleyRequest
    var productId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_comment)
        //get product id
        productId = intent.extras!!.getInt("productId").toString()
        //setup toolbar
        toolbar_auth_title.text = "نظرات محصول";
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        productCommentVolleyRequest = ProductCommentVolleyRequest(this@ProductCommentActivity)
        //setup recycler view
        productCommentRecyclerViewAdapter =
            ProductCommentRecyclerViewAdapter(this@ProductCommentActivity) {
                updateProductComment()
            }
        rcy_product_comment.layoutManager = LinearLayoutManager(this@ProductCommentActivity)
        rcy_product_comment.adapter = productCommentRecyclerViewAdapter
        updateProductComment()

        //show loading dialog
        //get comments form api


        //add comemnt
        p_comment_img_btn_send.setOnClickListener {
            val comment_text = p_comment_etxt_comment.text.toString()
            if (comment_text.isNotEmpty()) {
                p_comment_img_btn_send.visibility = View.GONE
                p_comment_progress.visibility = View.VISIBLE
                productCommentVolleyRequest.addProductComments(productId, comment_text) { success ->
                    p_comment_img_btn_send.visibility = View.VISIBLE
                    p_comment_progress.visibility = View.GONE
                    if (success) {
                        updateProductComment()
                        Toast.makeText(
                            this@ProductCommentActivity,
                            "نظر شما با موفقیت ثبت گردید.", Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@ProductCommentActivity,
                            "مشکلی پیش آمده است لطفا بعدا امتحان کنید", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun updateProductComment() {
        val loadingDialogProductComment = LoadingProcessDialog(this@ProductCommentActivity).create()
        loadingDialogProductComment.show()
        productCommentVolleyRequest.productComments(productId) { success, productComment ->
            loadingDialogProductComment.dismiss()
            if (success) {
                productCommentRecyclerViewAdapter.setData(productComment)
                if (productComment.size == 0) {
                    p_comment_empty_commnet.visibility = View.VISIBLE
                } else {
                    p_comment_empty_commnet.visibility = View.GONE
                }
            } else {
                Toast.makeText(
                    this@ProductCommentActivity,
                    "مشکلی پیش آمده است لطفا بعدا امتحان کنید", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}