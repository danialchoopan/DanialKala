package ir.danialchoopan.danialkala.data.api


object EndPoints {
    //base url
    private val domain = ApiDomain.domain
    val baseUrl = "$domain/api/"
    val storage = "$domain/"
    val storageImg = "$domain/"

    val home = "${baseUrl}home"
    val category = "${baseUrl}category"
    val showCategory = "${baseUrl}categoryproduct"

    //auth routes
    val register = "${baseUrl}auth/user/register"
    val login = "${baseUrl}auth/user/login"
    val tokenCheck = "${baseUrl}auth/user/checkToken"
    val getUserInfo = "${baseUrl}auth/user/getUserInfo"
    val updateUserInfo = "${baseUrl}auth/user/updateUserInfo"
    val checkVerifyPhone = "${baseUrl}auth/user/checkIfPhoneVerified"
    val sendSmsVerifyUserCode = "${baseUrl}auth/user/sendVerifyPhoneSms"
    val confirmVerifyPhoneSms = "${baseUrl}auth/user/confirmVerifyPhoneSms"
    //changePassword
    val changePassword="${baseUrl}auth/user/changePassword"
    //forgot password
    val checkUserPhoneForForgotPassword="${baseUrl}auth/user/checkUserPhoneForForgotPassword"
    val sendVerifyPhoneSmsForgotPassword="${baseUrl}auth/user/sendVerifyPhoneSmsForgotPassword"
    val confirmVerifyPhoneSmsForgotPassword="${baseUrl}auth/user/confirmVerifyPhoneSmsForgotPassword"
    val changePasswordForgot="${baseUrl}auth/user/changePasswordForgot"

    //emailchangePasswordForgot
    val sendVerifyEmail = "${baseUrl}auth/user/sendVerifyEmail"

    //product
    val showProduct = "${baseUrl}product"

    //all product
    val allProduct = "${baseUrl}show/all/product"

    //search product
    val searchProduct = "${baseUrl}product/search"

    //product comment
    val productComment = "${baseUrl}product/comment"

    val subCategory = "${baseUrl}subCategory"

    //user cart
    val userCart = "${baseUrl}user/cart"
    //end user cart

    //orders
    val addOrder="${baseUrl}user/order"


    //edit profile routes

    //user address routes
    val userAddress = "${baseUrl}user/addess"

    val states = "${baseUrl}states"
    //end states

    //favorite product
    val favoriteProductCheck = "${baseUrl}favorite/product/check"
    val likeProduct = "${baseUrl}favorite/product"
    val allFavoriteProduct = "${baseUrl}favorite/product/all"
    //end favorite product
}