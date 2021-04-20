package ir.danialchoopan.danialkala.data.api.home_page

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.StringRequest
import ir.danialchoopan.danialkala.data.api.EndPoints
import ir.danialchoopan.danialkala.data.api.VolleySingleTon
import ir.danialchoopan.danialkala.data.model.HomeSlider
import ir.danialchoopan.danialkala.data.model.ProductCategories
import ir.danialchoopan.danialkala.data.model.product.*
import org.json.JSONObject

class HomePageRequest(val context: Context) {
    fun getData(
        resultSliders: (resultUserNotes: ArrayList<HomeSlider>, requestResult: Boolean) -> Unit,
        resultCategories: (resultUserNotes: ArrayList<ProductCategories>, requestResult: Boolean) -> Unit,
        resultNewProducts: (resultUserNotes: ArrayList<Product>, requestResult: Boolean) -> Unit,
    ) {
        val homePageStringRequest =
            StringRequest(Request.Method.GET, EndPoints.home,
                { StringResponse ->
                    try {
                        val jsonRequest = JSONObject(StringResponse)

                        //home slider
                        val homeSliders = ArrayList<HomeSlider>()
                        val jsonArHomeSlider = jsonRequest.getJSONArray("home_slider")
                        for (homeSliderIndex in 0 until jsonArHomeSlider.length()) {
                            //home slider json
                            val jsonHomeSlider = jsonArHomeSlider.optJSONObject(homeSliderIndex)
                            //parse slider
                            homeSliders.add(
                                HomeSlider(
                                    jsonHomeSlider.getInt("id"),
                                    jsonHomeSlider.getString("title"),
                                    jsonHomeSlider.getString("slider_photo"),
                                    jsonHomeSlider.getString("created_at"),
                                    jsonHomeSlider.getString("updated_at"),

                                    )
                            )
                        }
                        //end parse ar home slider
                        resultSliders(homeSliders, true)

                        //product category
                        val categories = ArrayList<ProductCategories>()
                        val jsonArCategories = jsonRequest.getJSONArray("categories")
                        for (productCategoryIndex in 0 until jsonArCategories.length()) {
                            //category json
                            val jsonCategory =
                                jsonArCategories.optJSONObject(productCategoryIndex)
                            //category slider
                            categories.add(
                                ProductCategories(
                                    jsonCategory.getInt("id"),
                                    jsonCategory.getString("name"),
                                    jsonCategory.getString("category_photo"),
                                    jsonCategory.getString("created_at"),
                                    jsonCategory.getString("updated_at"),
                                )
                            )
                        }
                        //end parse ar category
                        resultCategories(categories, true)

                        //product
                        val products = ArrayList<Product>()
                        val jsonArProducts = jsonRequest.getJSONArray("new_products")
                        for (productIndex in 0 until jsonArProducts.length()) {
                            //product json
                            val jsonProduct =
                                jsonArProducts.optJSONObject(productIndex)

                            //parse color
                            val productColors = ArrayList<ProductColor>()
                            val jsonArProductColors = jsonProduct.getJSONArray("colors")
                            for (productColorIndex in 0 until jsonArProductColors.length()) {
                                val jsonProductColor =
                                    jsonArProductColors.getJSONObject(productColorIndex)
                                productColors.add(
                                    ProductColor(
                                        jsonProductColor.getInt("id"),
                                        jsonProductColor.getString("name"),
                                        jsonProductColor.getString("hex_code"),
                                        jsonProductColor.getString("created_at"),
                                        jsonProductColor.getString("updated_at"),

                                        )
                                )
                            }
                            //end parse color

                            //product brand
                            val jsonProductBrand = jsonProduct.getJSONObject("brand")
                            val productBrand = ProductBrand(
                                jsonProductBrand.getInt("id"),
                                jsonProductBrand.getString("name"),
                                "",
                                jsonProductBrand.getString("created_at"),
                                jsonProductBrand.getString("updated_at"),
                            )
                            //end product brand

                            //product stores
                            val productStores = ArrayList<ProductStore>()
                            val jsonArProductStores = jsonProduct.getJSONArray("productStore")
                            for (productStoreIndex in 0 until jsonArProductStores.length()) {
                                val jsonProductStore =
                                    jsonArProductStores.getJSONObject(productStoreIndex)
                                productStores.add(
                                    ProductStore(
                                        jsonProductStore.getInt("id"),
                                        jsonProductStore.getInt("color_id"),
                                        jsonProductStore.getInt("count"),
                                        jsonProductStore.getString("warranty"),
                                        jsonProductStore.getString("price_sell"),
                                        jsonProductStore.getString("created_at"),
                                        jsonProductStore.getString("updated_at")
                                    )
                                )
                            }
                            //end product stores

                            // product photos
                            val productPhotos = ArrayList<ProductPhoto>()
                            val jsonArProductPhotos = jsonProduct.getJSONArray("productphotos")
                            for (productPhotosIndex in 0 until jsonArProductPhotos.length()) {
                                val jsonProductPhoto =
                                    jsonArProductStores.getJSONObject(productPhotosIndex)
                                productPhotos.add(
                                    ProductPhoto(
                                        jsonProductPhoto.getInt("id"),
                                        jsonProductPhoto.getString("path"),
                                        jsonProductPhoto.getInt("thumbnail"),
                                        jsonProductPhoto.getString("created_at"),
                                        jsonProductPhoto.getString("updated_at")

                                    )
                                )
                            }
                            //end product photos


                            //parse product
                            products.add(
                                Product(
                                    jsonProduct.getInt("id"),
                                    jsonProduct.getString("name"),
                                    jsonProduct.getString("description"),
                                    productColors,
                                    jsonProduct.getString("category"),
                                    jsonProduct.getString("Subcategory"),
                                    productBrand,
                                    jsonProduct.getString("status"),
                                    jsonProduct.getString("thumbnail"),
                                    jsonProduct.getString("price"),
                                    productStores,
                                    productPhotos,
                                    jsonProduct.getString("created_at"),
                                    jsonProduct.getString("updated_at")
                                )
                            )
                        }
                        //end parse ar product
                        resultCategories(categories, true)


                    } catch (e: Exception) {
                        resultSliders(ArrayList<HomeSlider>(), false)
                        resultCategories(ArrayList<ProductCategories>(), false)
                        resultNewProducts(ArrayList<Product>(), false)
                    }
                },
                { error ->
                    error.printStackTrace()
                })
        //end request home page
        VolleySingleTon.getInstance(context).addToRequestQueue(homePageStringRequest)
    }
}