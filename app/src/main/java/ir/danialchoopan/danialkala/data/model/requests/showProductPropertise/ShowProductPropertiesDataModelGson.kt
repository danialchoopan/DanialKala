package ir.danialchoopan.danialkala.data.model.requests.showProductPropertise

data class ShowProductPropertiesDataModelGson(
    val brand_id: String,
    val colors: String,
    val created_at: String,
    val description: String,
    val id: Int,
    val name: String,
    val properties_products: List<PropertiesProduct>,
    val status: String,
    val subCategory_id: String,
    val updated_at: String
)