package ir.danialchoopan.danialkala.data.model.requests.register

data class User(
    val created_at: String,
    val email: String,
    val email_verified_at: Any,
    val id: Int,
    val name: String,
    val phone: String,
    val phone_verified: String,
    val role: String,
    val updated_at: String
)