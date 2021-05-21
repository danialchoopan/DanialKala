package ir.danialchoopan.danialkala.data.model.requests.subCategory

data class SubCategoryItem(
    val category_id: Int,
    val created_at: String,
    val id: Int,
    val name: String,
    val photo: Photo,
    val photo_id: Int,
    val updated_at: String
)