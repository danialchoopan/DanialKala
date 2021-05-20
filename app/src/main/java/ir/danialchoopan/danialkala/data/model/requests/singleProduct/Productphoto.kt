package ir.danialchoopan.danialkala.data.model.requests.singleProduct

data class Productphoto(
    val created_at: String,
    val id: Int,
    val path: String,
    val product_id: Int,
    val thumbnail: Int,
    val updated_at: String
)