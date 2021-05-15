package ir.danialchoopan.danialkala.data.model.requests.register

data class RegisterUserDataModel(
    val message: String,
    val success: Boolean,
    val token: String,
    val user: User
)