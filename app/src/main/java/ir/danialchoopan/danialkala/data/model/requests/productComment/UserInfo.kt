package ir.danialchoopan.danialkala.data.model.requests.productComment

data class UserInfo(
    val created_at: String,
    val email: String,
    val email_verified_at: Any,
    val id: Int,
    val name: String,
    val phone: String,
    val phone_verified: Int,
    val role: Int,
    val updated_at: String
)