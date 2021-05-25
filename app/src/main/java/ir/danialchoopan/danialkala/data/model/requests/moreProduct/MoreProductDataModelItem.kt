package ir.danialchoopan.danialkala.data.model.requests.moreProduct

data class MoreProductDataModelItem(
    val Subcategory: String,
    val brand: Brand,
    val brand_id: Int,
    val category: String,
    val colors: List<Color>,
    val created_at: String,
    val description: String,
    val id: Int,
    val name: String,
    val price: String,
    val productphotos: List<Productphoto>,
    val status: String,
    val stores: List<Store>,
    val subCategory_id: Int,
    val thumbnail: String,
    val updated_at: String
)