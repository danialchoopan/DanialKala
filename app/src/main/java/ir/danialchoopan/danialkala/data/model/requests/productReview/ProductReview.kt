package ir.danialchoopan.danialkala.data.model.requests.productReview

data class ProductReview(
    val created_at: String,
    val id: Int,
    val product_id: String,
    val review: String,
    val updated_at: String
)