package ir.danialchoopan.danialkala.data.model.requests.orders

data class OrderDataModelItem(
    val created_at: String,
    val description: String,
    val id: Int,
    val id_transaction: String,
    val link_transaction: String,
    val order_product_id: String,
    val price: Int,
    val status: Int,
    val updated_at: String,
    val user_addesse_id: Int,
    val user_id: Int
)