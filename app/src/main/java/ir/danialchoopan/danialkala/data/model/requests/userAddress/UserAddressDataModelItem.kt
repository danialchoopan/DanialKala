package ir.danialchoopan.danialkala.data.model.requests.userAddress

data class UserAddressDataModelItem(
    val addess_phone: String,
    val address: String,
    val city_code: String,
    val city_name: String,
    val created_at: String,
    val id: Int,
    val lanline_phone: String,
    val post_code: String,
    val state_name: String,
    val updated_at: String,
    val user_id: Int
)