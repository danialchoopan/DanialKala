package ir.danialchoopan.danialkala.adapter.comment

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.data.api.volleyRequestes.product.comments.ProductCommentVolleyRequest
import ir.danialchoopan.danialkala.data.model.requests.productComment.ProductCommentItem
import kotlinx.android.synthetic.main.row_item_recycler_view_product_comment.view.*

class ProductCommentRecyclerViewAdapter(
    private val m_context: Context,
    val onDelete: () -> Unit
) :
    RecyclerView.Adapter<ProductCommentRecyclerViewAdapter.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    var ar_data = emptyList<ProductCommentItem>()

    fun setData(ar_data: List<ProductCommentItem>) {
        this.ar_data = ar_data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.row_item_recycler_view_product_comment, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val commentItem = ar_data[position]
        holder.view.row_comment_user_name.text = commentItem.userInfo.name
        holder.view.row_comment_comment_text.text = commentItem.productComment.comment
        holder.view.row_comment_date.text = commentItem.productComment.created_at
        val userSharePreferences = UserSharePreferences(m_context).sharePreferences
        if (commentItem.userInfo.id == userSharePreferences.getInt("id", 0)) {
            holder.view.row_comment_img_delete.visibility = View.VISIBLE
        } else {
            holder.view.row_comment_img_delete.visibility = View.GONE
        }
        //delete
        holder.view.row_comment_img_delete.setOnClickListener {
            AlertDialog.Builder(m_context).also { builder ->
                builder.setTitle("حذف کامنت")
                builder.setMessage("آیا از حذف کامنت خود مطمئن هستید")
                builder.setPositiveButton("حذف") { _, _ ->
                    ProductCommentVolleyRequest(m_context).deleteComment(commentItem.productComment.id.toString()) { success ->
                        if (success) {
                            onDelete()
                        } else {
                            Toast.makeText(
                                m_context,
                                "مشکلی پیش آمده است لطفا بعدا امتحان کنید", Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
                builder.setNegativeButton("نه شوخی کردم") { _, _ ->

                }
            }.show()
        }
    }

    override fun getItemCount(): Int = ar_data.size
}