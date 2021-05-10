package ir.danialchoopan.danialkala.fragments.home
//
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.StaggeredGridLayoutManager
//import ir.danialchoopan.danialkala.R
//import ir.danialchoopan.danialkala.adapter.category.RecyclerViewCategoryProductHome
//import ir.danialchoopan.danialkala.adapter.slider.RecyclerViewSliderHome
//import ir.danialchoopan.danialkala.data.model.ProductCategories
//import kotlinx.android.synthetic.main.fragment_home.view.*
//
//class HomeFragment : Fragment() {
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val sliderAdapter = RecyclerViewSliderHome()
//        val categoryAdapter = RecyclerViewCategoryProductHome()
//        view.vpgHomeSlider.adapter = sliderAdapter
//
//        view.rycCategories.layoutManager =
//            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)
//        view.rycCategories.adapter = categoryAdapter
//
//
//        view.custom_product_new_product.inflateRecyclerViewProduct()
//        view.custom_product_new_product.setRecyclerViewLayoutManager()
//        view.custom_product_new_product.setRecyclerViewAdapter()
//
//
//        //view model
//        val homePageViewModel = ViewModelProvider(this)[HomePageViewModel::class.java]
//        //send home request
//        homePageViewModel.getHomePageData()
//
//        //observe
//        homePageViewModel.homePageData.observe(viewLifecycleOwner, { homePageViewModel ->
//
//            //set slider
////            sliderAdapter.setData(homePageViewModel.home_slider)
//
//            //set category
//            categoryAdapter.setData(homePageViewModel.categories)
//
//
//            //set products
//            view.custom_product_new_product.setRecyclerViewData(
//                homePageViewModel.new_products,
//                "محصولات جدید"
//            )
//
//
//        })
////
////        HomePageRequest(requireContext()).getData(
////            //slider
////            { resultSlider, requestResult ->
////
//////                Log.d("resultSlider", resultSlider.size.toString())
//////                if (requestResult) {
//////                    sliderAdapter.setData(resultSlider)
//////                } else {
//////                    Toast.makeText(
//////                        requireContext(),
//////                        R.string.errorConnectionSnackBar,
//////                        Toast.LENGTH_SHORT
//////                    ).show()
//////                }
////
////            },
////            //categories
////            { resultCategory, requestResult ->
////                Log.d("resultCategory", resultCategory.size.toString())
////                if (requestResult) {
////                    categoryAdapter.setData(resultCategory)
////                } else {
////                    Toast.makeText(
////                        requireContext(),
////                        R.string.errorConnectionSnackBar,
////                        Toast.LENGTH_SHORT
////                    ).show()
////                }
////            },
////            //new product
////            { resultNewProducts, requestResult ->
////                Log.d("resultNewProducts", resultNewProducts.size.toString())
////                val datapp = listOf<Product>(
////                    Product(
////                        0,
////                        "name",
////                        "description",
////                        arrayListOf<ProductColor>(
////                            ProductColor(0, "dasda", "dasd", "dasda", "dasd"),
////                            ProductColor(0, "dasda", "dasd", "dasda", "dasd"),
////                        ),
////                        "category",
////                        "Subcategory",
////                        ProductBrand(0, "sda", "dasdas", "dasda", "dasda"),
////                        "status",
////                        "thumbnail",
////                        "10000",
////                        arrayListOf<ProductStore>(
////                            ProductStore(0, 1, 0, "dasda", "dasd", "2", "1"),
////                            ProductStore(0, 1, 0, "dasda", "dasd", "2", "1"),
////                            ProductStore(0, 1, 0, "dasda", "dasd", "2", "1"),
////                        ),
////                        arrayListOf<ProductPhoto>(
////                            ProductPhoto(0, "dasda", 0, "dasda", "dasd"),
////                            ProductPhoto(0, "dasda", 1, "dasda", "dasd"),
////                            ProductPhoto(0, "dasda", 0, "dasda", "dasd"),
////                        ),
////                        "created_at",
////                        "updated_at"
////                    ),
////                    Product(
////                        0,
////                        "name",
////                        "description",
////                        arrayListOf<ProductColor>(
////                            ProductColor(0, "dasda", "dasd", "dasda", "dasd"),
////                            ProductColor(0, "dasda", "dasd", "dasda", "dasd"),
////                        ),
////                        "category",
////                        "Subcategory",
////                        ProductBrand(0, "sda", "dasdas", "dasda", "dasda"),
////                        "status",
////                        "thumbnail",
////                        "10000",
////                        arrayListOf<ProductStore>(
////                            ProductStore(0, 1, 0, "dasda", "dasd", "2", "1"),
////                            ProductStore(0, 1, 0, "dasda", "dasd", "2", "1"),
////                            ProductStore(0, 1, 0, "dasda", "dasd", "2", "1"),
////                        ),
////                        arrayListOf<ProductPhoto>(
////                            ProductPhoto(0, "dasda", 0, "dasda", "dasd"),
////                            ProductPhoto(0, "dasda", 1, "dasda", "dasd"),
////                            ProductPhoto(0, "dasda", 0, "dasda", "dasd"),
////                        ),
////                        "created_at",
////                        "updated_at"
////                    ),
////                    Product(
////                        0,
////                        "name",
////                        "description",
////                        arrayListOf<ProductColor>(
////                            ProductColor(0, "dasda", "dasd", "dasda", "dasd"),
////                            ProductColor(0, "dasda", "dasd", "dasda", "dasd"),
////                        ),
////                        "category",
////                        "Subcategory",
////                        ProductBrand(0, "sda", "dasdas", "dasda", "dasda"),
////                        "status",
////                        "thumbnail",
////                        "10000",
////                        arrayListOf<ProductStore>(
////                            ProductStore(0, 1, 0, "dasda", "dasd", "2", "1"),
////                            ProductStore(0, 1, 0, "dasda", "dasd", "2", "1"),
////                            ProductStore(0, 1, 0, "dasda", "dasd", "2", "1"),
////                        ),
////                        arrayListOf<ProductPhoto>(
////                            ProductPhoto(0, "dasda", 0, "dasda", "dasd"),
////                            ProductPhoto(0, "dasda", 1, "dasda", "dasd"),
////                            ProductPhoto(0, "dasda", 0, "dasda", "dasd"),
////                        ),
////                        "created_at",
////                        "updated_at"
////                    ),
////                    Product(
////                        0,
////                        "name",
////                        "description",
////                        arrayListOf<ProductColor>(
////                            ProductColor(0, "dasda", "dasd", "dasda", "dasd"),
////                            ProductColor(0, "dasda", "dasd", "dasda", "dasd"),
////                        ),
////                        "category",
////                        "Subcategory",
////                        ProductBrand(0, "sda", "dasdas", "dasda", "dasda"),
////                        "status",
////                        "thumbnail",
////                        "10000",
////                        arrayListOf<ProductStore>(
////                            ProductStore(0, 1, 0, "dasda", "dasd", "2", "1"),
////                            ProductStore(0, 1, 0, "dasda", "dasd", "2", "1"),
////                            ProductStore(0, 1, 0, "dasda", "dasd", "2", "1"),
////                        ),
////                        arrayListOf<ProductPhoto>(
////                            ProductPhoto(0, "dasda", 0, "dasda", "dasd"),
////                            ProductPhoto(0, "dasda", 1, "dasda", "dasd"),
////                            ProductPhoto(0, "dasda", 0, "dasda", "dasd"),
////                        ),
////                        "created_at",
////                        "updated_at"
////                    ),
////
////
////                    )
////
////                view.custom_product_new_product.setRecyclerViewData(
////                    datapp,
////                    "محصولات جدید"
////                )
////
////                if (requestResult) {
////
//////                    view.custom_product_new_product.setRecyclerViewData(
//////                        resultNewProducts,
//////                        "محصولات جدید"
//////                    )
////
////                } else {
////                    Toast.makeText(
////                        requireContext(),
////                        R.string.errorConnectionSnackBar,
////                        Toast.LENGTH_SHORT
////                    ).show()
////                }
////
////            })
//        //end home request
//
//
//    }
//
//}