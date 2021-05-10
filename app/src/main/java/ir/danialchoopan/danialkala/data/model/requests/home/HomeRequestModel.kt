package ir.danialchoopan.danialkala.data.model.requests.home
import com.google.gson.annotations.SerializedName

data class HomeRequestModel(

	@SerializedName("home_slider") val home_slider : List<Home_slider>,
	@SerializedName("categories") val categories : List<Categories>,
	@SerializedName("new_products") val new_products : List<New_products>
)