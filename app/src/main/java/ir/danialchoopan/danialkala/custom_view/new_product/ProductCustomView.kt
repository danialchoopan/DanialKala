package ir.danialchoopan.danialkala.custom_view.new_product

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.model.requests.home.New_products
import kotlinx.android.synthetic.main.view_products_home.view.*

@SuppressLint("ViewConstructor")
class ProductCustomView : RelativeLayout {
    lateinit var lblNameProduct: TextView
    lateinit var lblMoreProduct: TextView
    lateinit var rcyProduct: RecyclerView
    lateinit var rcyProductAdapter: RecyclerViewProductCustomView

    init {
        inflateRecyclerViewProduct()
    }

    constructor(
        context: Context?
    ) : super(context) {

        inflateRecyclerViewProduct()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        inflateRecyclerViewProduct()

    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        inflateRecyclerViewProduct()

    }


    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        inflateRecyclerViewProduct()
    }


    fun inflateRecyclerViewProduct() {
        val view = inflate(context, R.layout.view_products_home, this)
        lblNameProduct = view.rowProductName
        lblMoreProduct = view.rowMoreProduct

        //rcy adapter
        rcyProduct = view.rcyProduct
    }

    fun onMoreClickListener(result: () -> Unit) {
        lblMoreProduct.setOnClickListener {
            result()
        }
    }

    fun setRecyclerViewAdapter() {
        rcyProductAdapter = RecyclerViewProductCustomView(context)
        rcyProduct.adapter = rcyProductAdapter
    }

    fun setRecyclerViewLayoutManager() {
        rcyProduct.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
    }

    fun setRecyclerViewData(products: List<New_products>, titleHeader: String) {
        lblNameProduct.text = titleHeader
        rcyProductAdapter.setData(products)
    }
}