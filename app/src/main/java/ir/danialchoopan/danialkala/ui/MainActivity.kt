package ir.danialchoopan.danialkala.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
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
import ir.danialchoopan.danialkala.dialog.ErrorDialog
import ir.danialchoopan.danialkala.dialog.SuccessDialog
import ir.danialchoopan.danialkala.fragments.home.HomePageViewModel
import ir.danialchoopan.danialkala.ui.about.AboutUsActivity
import ir.danialchoopan.danialkala.ui.cart.UserCartActivity
import ir.danialchoopan.danialkala.ui.category.ProductCategoryActivity
import ir.danialchoopan.danialkala.ui.profile.UserProfileActivity
import ir.danialchoopan.danialkala.ui.searchProduct.SearchActivity
import ir.danialchoopan.danialkala.ui.showAllProduct.MoreProductLayoutActivity
import ir.danialchoopan.danialkala.ui.userAuth.UserRegisterActivity
import ir.danialchoopan.danialkala.utails.LoadGravatarProfileUser
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_show_product.*

class MainActivity : AppCompatActivity() {
    lateinit var authUserVolleyRequest: AuthUserVolleyRequest
    lateinit var userSharePreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userSharePreferences = UserSharePreferences(this@MainActivity).sharePreferences
        authUserVolleyRequest = AuthUserVolleyRequest(this@MainActivity)

        val intentPay = intent.getStringExtra("success").toString()

        if (intentPay == "ture") {
            val successDialog = SuccessDialog(this@MainActivity)
            val createSuccessDialog = successDialog.create()
            successDialog.layout.findViewById<TextView>(R.id.alert_success_btn_dismiss)
                .setOnClickListener {
                    createSuccessDialog.dismiss()
                }
            createSuccessDialog.show()
        }

        if (intentPay == "false") {
            val errorDialog = ErrorDialog(this@MainActivity)
            val errorDialogDialog = errorDialog.create()
            errorDialog.layout.findViewById<TextView>(R.id.alert_close_btn_dismiss)
                .setOnClickListener {
                    errorDialogDialog.dismiss()
                }
            errorDialogDialog.show()
        }

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

        //set recycler adapters
        val rycAdapterCategories = RecyclerViewCategoryProductHome(this@MainActivity)
        rycCategories.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
        rycCategories.adapter = rycAdapterCategories
        //custom view
        cProduct_new_products.inflateRecyclerViewProduct()
        cProduct_new_products.setRecyclerViewLayoutManager()
        cProduct_new_products.setRecyclerViewAdapter()
        cProduct_new_products.onMoreClickListener {
            Intent(this@MainActivity, MoreProductLayoutActivity::class.java).also { intent ->
                startActivity(intent)
            }
        }

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
        LoadGravatarProfileUser(this@MainActivity).LoadImage(
            nav_menu_main_activity.getHeaderView(0)
                .findViewById<ImageView>(R.id.profile_user_profile_gravatar_header)
        )
        //on click nav
        nav_menu_main_activity.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_menu_item_list_categories -> {
                    Intent(this@MainActivity, ProductCategoryActivity::class.java).also { intent ->
                        startActivity(intent)
                    }
                }
                R.id.navigation_menu_item_cart_shop -> {
                    Intent(this@MainActivity, UserCartActivity::class.java).also { intent ->
                        startActivity(intent)
                    }
                }
                R.id.navigation_menu_item_about_us -> {
                    Intent(this@MainActivity, AboutUsActivity::class.java).also { intent ->
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
        var count_cart = 0
        val cartVolleyRequest = CartVolleyRequest(this@MainActivity)
        cartVolleyRequest.userCart { success, userCart ->
            if (success) {
                toolbar_img_btn_user_cart_tv_count.text = userCart.size.toString()
                count_cart = userCart.size
            }
        }
        userSharePreferences = UserSharePreferences(this@MainActivity).sharePreferences
        //on click auth btn on
        val auth_header_lable =
            nav_menu_main_activity.getHeaderView(0).findViewById<TextView>(R.id.tv_auth_header_nav)
        val cart_manu_item =
            nav_menu_main_activity.menu.findItem(R.id.navigation_menu_item_cart_shop)
        if (userSharePreferences.contains("token")) {
            cart_manu_item.actionView.findViewById<TextView>(R.id.action_tv_cart_manu).text =
                count_cart.toString()
            cart_manu_item.isVisible = true
        } else {
            cart_manu_item.isVisible = false

        }
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

        if (userSharePreferences.contains("token")) {
            authUserVolleyRequest.checkToken { success ->
                if (success) {
                    authUserVolleyRequest.checkIfPhoneVerified { verified ->
                        if (!verified) {
                            UserSharePreferences(this@MainActivity).sharePreferences.edit().clear()
                                .apply()
                            main_layout_cart.visibility = View.GONE
                        }
                    }

                } else {
                    UserSharePreferences(this@MainActivity).sharePreferences.edit().clear().apply()
                    Toast.makeText(this@MainActivity, "ورود شما منفضی شده است", Toast.LENGTH_SHORT)
                        .show()

                    main_layout_cart.visibility = View.GONE
                }
            }//end check token
        } else {
            main_layout_cart.visibility = View.GONE
        }


    }
}