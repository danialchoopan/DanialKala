package ir.danialchoopan.danialkala.data.model.requests.productComment

data class ProductCommentX(
    val comment: String,
    val created_at: String,
    val id: Int,
    val product_id: Int,
    val updated_at: String,
    val user_id: Int
)