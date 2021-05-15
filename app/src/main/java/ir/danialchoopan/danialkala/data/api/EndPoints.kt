package ir.danialchoopan.danialkala.data.api

object EndPoints {
    const private val domain="http://192.168.43.166:8000"
    const val baseUrl = "$domain/api/"
    const val storage = "$domain/storage/"
    const val storageImg = "$domain/"
    const val home = "${baseUrl}home"
    const val category = "${baseUrl}category"
    const val showCategory = "${baseUrl}categoryproduct/"

    //auth routes
    const val register = "${baseUrl}auth/user/register/"
    const val login = "${baseUrl}auth/user/login/"
    const val tokenCheck= "${baseUrl}auth/user/checkToken/"
    const val getUserInfo = "${baseUrl}auth/user/getUserInfo/"
    const val checkVerifyPhone = "${baseUrl}auth/user/checkIfPhoneVerified/"
    const val sendSmsVerifyUserCode = "${baseUrl}auth/user/sendVerifyPhoneSms/"
    const val confirmVerifyPhoneSms = "${baseUrl}auth/user/confirmVerifyPhoneSms/"
}