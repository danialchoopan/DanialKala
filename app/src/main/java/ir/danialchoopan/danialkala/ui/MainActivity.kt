package ir.danialchoopan.danialkala.ui

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.MenuItem
import android.view.SubMenu
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.category.RecyclerViewCategoryProductHome
import ir.danialchoopan.danialkala.adapter.slider.ImgSliderViewPagerAdapter
import ir.danialchoopan.danialkala.fragments.home.HomePageViewModel
import ir.danialchoopan.danialkala.ui.category.ProductCategoryActivity
import ir.danialchoopan.danialkala.ui.searchProduct.SearchActivity
import ir.danialchoopan.danialkala.utails.CustomTypefaceSpan
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var iranianSansFont: Typeface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set toolbar and navigation menu
        setSupportActionBar(toolbar_main_activity)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        val actionbarToggle = ActionBarDrawerToggle(
            this@MainActivity, drawer_layout_main_activity, toolbar_main_activity,
            R.string.open, R.string.close
        )
        drawer_layout_main_activity.addDrawerListener(actionbarToggle)
        actionbarToggle.syncState()
        //end nav menu
        //set font family nav menus
        val menu_item_nav = nav_menu_main_activity.menu
        for (i in 0 until menu_item_nav.size()) {
            val mi = menu_item_nav.getItem(i)
            if (mi.subMenu != null) {
                val subMenu: SubMenu = mi.subMenu
                if (subMenu.size() > 0) {
                    for (j in 0 until subMenu.size()) {
                        val subMenuItem = subMenu.getItem(j)
                        applyFontToMenuItem(subMenuItem)
                    }
                }
            }
            applyFontToMenuItem(mi)
        }
        //end set nav menu font family


        //set recycler adapters
        val rycAdapterCategories = RecyclerViewCategoryProductHome()
        rycCategories.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        rycCategories.adapter = rycAdapterCategories
        //custom view
        cProduct_new_products.inflateRecyclerViewProduct()
        cProduct_new_products.setRecyclerViewLayoutManager()
        cProduct_new_products.setRecyclerViewAdapter()

        //view model
        val viewModelHome = ViewModelProvider(this)[HomePageViewModel::class.java]
        //send request
        viewModelHome.getHomePageData()
        //observe
        viewModelHome.homePageData.observe(this, Observer { homePageViewModel ->

            //set category
            rycAdapterCategories.setData(homePageViewModel.categories)

            //set custom view new product
            cProduct_new_products.setRecyclerViewData(
                homePageViewModel.new_products,
                "جدید ترین محصولات"
            )
            vpSliderMainPage.adapter =
                ImgSliderViewPagerAdapter(this@MainActivity, homePageViewModel.home_slider)
        });

        //on click nav
        nav_menu_main_activity.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_menu_item_list_categories -> {
                    Intent(this@MainActivity, ProductCategoryActivity::class.java).also { intent ->
                        startActivity(intent)
                    }
                }
            }
            false
        }

        //open search activity
        toolbar_img_btn_search_icon.setOnClickListener {
            Intent(this@MainActivity, SearchActivity::class.java).also {intent ->
                startActivity(intent)
            }
        }


    }

    private fun applyFontToMenuItem(mi: MenuItem) {
        val mNewTitle = SpannableString(mi.getTitle())
        mNewTitle.setSpan(
            CustomTypefaceSpan(
                applicationContext, "", getIranianSansFont(
                    applicationContext
                )
            ), 0, mNewTitle.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        mi.setTitle(mNewTitle)
    }

    fun getIranianSansFont(context: Context): Typeface? {
        if (iranianSansFont == null) {
            iranianSansFont =
                Typeface.createFromAsset(context.getAssets(), "fonts/iranian_sans.ttf")
        }
        return iranianSansFont
    }
}