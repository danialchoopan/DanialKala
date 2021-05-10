package ir.danialchoopan.danialkala.data.model.requests.showProductPropertise

data class PropertiesProduct(
    val created_at: String,
    val id: Int,
    val name: String,
    val product_id: String,
    val sub_properties_product: List<SubPropertiesProduct>,
    val updated_at: String
)