package ir.danialchoopan.danialkala.data.model.requests.userCart

data class Cart(
    val created_at: String,
    val id: Int,
    val product_id: Int,
    val updated_at: String,
    val user_id: Int
)