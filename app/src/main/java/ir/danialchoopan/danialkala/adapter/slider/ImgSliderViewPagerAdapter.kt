package ir.danialchoopan.danialkala.adapter.slider

import android.content.Context
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.model.requests.home.Home_slider
import kotlinx.android.synthetic.main.row_img_slider_item.view.*

class ImgSliderViewPagerAdapter(val m_context: Context, val ar_data: List<Home_slider>) :
    PagerAdapter() {
    override fun getCount(): Int = ar_data.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (view == `object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(m_context).inflate(R.layout.row_img_slider_item, null)
        val img_view_item = view.row_img_slider_item
        Picasso.get()
            .load(EndPoints.storage + ar_data[position].slider_photo).into(img_view_item)
        Log.i("slider photo","${EndPoints.storage + ar_data[position].slider_photo}")
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}