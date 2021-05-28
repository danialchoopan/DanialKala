package ir.danialchoopan.danialkala.utails


import android.content.Context
import ir.danialchoopan.danialkala.R
import android.widget.ImageView
import com.squareup.picasso.Picasso
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.data.api.EndPoints
import kotlinx.android.synthetic.main.row_order_product_recycler_view.view.*
import java.math.BigInteger
import java.security.MessageDigest


class LoadGravatarProfileUser(private val m_context: Context) {
    private val userSharePreferences = UserSharePreferences(m_context).sharePreferences

    fun LoadImage(imageView: ImageView) {
        val hash: String = md5(userSharePreferences.getString("email", "").toString())
        val gravatarUrl = "http://www.gravatar.com/avatar/$hash?s=204&d=404"

        Picasso.get().load(gravatarUrl)
            .placeholder(R.drawable.ic_baseline_person_24)
            .into(imageView)
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

}