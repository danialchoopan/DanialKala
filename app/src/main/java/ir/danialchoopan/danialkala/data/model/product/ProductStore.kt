package ir.danialchoopan.danialkala.data.model.product

data class ProductStore(
    val id:Int,
    val color_id:Int,
    val count:Int,
    val warranty:String,
    val price:String,
    val created_at: String,
    val updated_at: String
)
