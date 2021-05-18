package ir.danialchoopan.danialkala.data.model.requests.favoriteProduct

data class Store(
    val color_id: Int,
    val count: Int,
    val created_at: String,
    val id: Int,
    val price_buy: String,
    val price_sell: String,
    val product_id: Int,
    val updated_at: String,
    val warranty: String
)