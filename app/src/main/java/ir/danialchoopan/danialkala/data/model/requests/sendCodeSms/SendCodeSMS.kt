package ir.danialchoopan.danialkala.data.model.requests.sendCodeSms

data class SendCodeSMS(
    val message: String,
    val response_code: Int,
    val success: Boolean
)