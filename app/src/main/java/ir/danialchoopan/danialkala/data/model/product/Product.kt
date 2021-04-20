package ir.danialchoopan.danialkala.data.model.product

import ir.danialchoopan.danialkala.data.model.product.ProductColor

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val colors: ArrayList<ProductColor>,
    val category: String,
    val subCategory: String,
    val brand: ProductBrand,
    val status: String,
    val thumbnail: String,
    val price: String,
    val stores: ArrayList<ProductStore>,
    val productPhotos: ArrayList<ProductPhoto>,
    val created_at: String,
    val updated_at: String
)
