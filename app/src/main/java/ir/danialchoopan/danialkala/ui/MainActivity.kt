package ir.danialchoopan.danialkala.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.MenuItem
import android.view.SubMenu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.category.RecyclerViewCategoryProductHome
import ir.danialchoopan.danialkala.adapter.slider.ImgSliderViewPagerAdapter
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.data.api.volleyRequestes.auth.AuthUserVolleyRequest
import ir.danialchoopan.danialkala.data.api.volleyRequestes.cart.CartVolleyRequest
import ir.danialchoopan.danialkala.fragments.home.HomePageViewModel
import ir.danialchoopan.danialkala.ui.cart.UserCartActivity
import ir.danialchoopan.danialkala.ui.category.ProductCategoryActivity
import ir.danialchoopan.danialkala.ui.profile.UserProfileActivity
import ir.danialchoopan.danialkala.ui.searchProduct.SearchActivity
import ir.danialchoopan.danialkala.ui.userAuth.UserRegisterActivity
import ir.danialchoopan.danialkala.utails.CustomTypefaceSpan
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var authUserVolleyRequest: AuthUserVolleyRequest
    lateinit var userSharePreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        authUserVolleyRequest = AuthUserVolleyRequest(this@MainActivity)
        authUserVolleyRequest.checkToken { success ->
            if (!success) {
                UserSharePreferences(this@MainActivity).sharePreferences.edit().clear().apply()
                Toast.makeText(this@MainActivity, "ورود شما منفضی شده است", Toast.LENGTH_SHORT)
                    .show()
                main_layout_cart.visibility = View.GONE
            } else {
                authUserVolleyRequest.checkIfPhoneVerified { verified ->
                    if (!verified) {
                        UserSharePreferences(this@MainActivity).sharePreferences.edit().clear()
                            .apply()
                        Toast.makeText(
                            this@MainActivity,
                            "ورود شما منفضی شده است",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        Toast.makeText(
                            this@MainActivity,
                            "لطفا شماره همراه خود را تایید کنید",
                            Toast.LENGTH_SHORT
                        )
                            .show()

                        main_layout_cart.visibility = View.GONE
                    }
                }
            }
        }//end check token
        userSharePreferences = UserSharePreferences(this@MainActivity).sharePreferences
        //set toolbar and navigation menu
        setSupportActionBar(toolbar_main_activity)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        val actionbarToggle = ActionBarDrawerToggle(
            this@MainActivity, drawer_layout_main_activity, toolbar_main_activity,
            R.string.open, R.string.close
        )
        actionbarToggle.drawerArrowDrawable.color = getColor(R.color.white)
        drawer_layout_main_activity.addDrawerListener(actionbarToggle)
        actionbarToggle.syncState()
        //end nav menu
        //set font family nav menus
//        val menu_item_nav = nav_menu_main_activity.menu
//        for (i in 0 until menu_item_nav.size()) {
//            val mi = menu_item_nav.getItem(i)
//            if (mi.subMenu != null) {
//                val subMenu: SubMenu = mi.subMenu
//                if (subMenu.size() > 0) {
//                    for (j in 0 until subMenu.size()) {
//                        val subMenuItem = subMenu.getItem(j)
//                        applyFontToMenuItem(subMenuItem)
//                    }
//                }
//            }
//            applyFontToMenuItem(mi)
//        }
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
            Intent(this@MainActivity, SearchActivity::class.java).also { intent ->
                startActivity(intent)
            }
        }

        //open cart activity
        toolbar_img_btn_user_cart.setOnClickListener {
            Intent(this@MainActivity, UserCartActivity::class.java).also { intent ->
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val cartVolleyRequest = CartVolleyRequest(this@MainActivity)
        cartVolleyRequest.userCart { success, userCart ->
            if (success) {
                toolbar_img_btn_user_cart_tv_count.text = userCart.size.toString()
            }
        }
        userSharePreferences = UserSharePreferences(this@MainActivity).sharePreferences
        //on click auth btn on
        val auth_header_lable =
            nav_menu_main_activity.getHeaderView(0).findViewById<TextView>(R.id.tv_auth_header_nav)
        val userNameShare = userSharePreferences.getString("name", "")
        if (userNameShare!!.isNotEmpty()) {
            auth_header_lable.text = userNameShare
            auth_header_lable.setOnClickListener {
                Intent(this@MainActivity, UserProfileActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }
        } else {
            auth_header_lable.text = "نام نویسی / ورود"
            auth_header_lable.setOnClickListener {
                Intent(this@MainActivity, UserRegisterActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }
        }
        authUserVolleyRequest.checkToken { success ->
            if (!success) {
                UserSharePreferences(this@MainActivity).sharePreferences.edit().clear().apply()
                main_layout_cart.visibility = View.GONE
            } else {
                main_layout_cart.visibility = View.VISIBLE
            }
        }//end check token
    }
}