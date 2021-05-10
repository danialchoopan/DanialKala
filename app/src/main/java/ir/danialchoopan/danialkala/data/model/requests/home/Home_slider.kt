package ir.danialchoopan.danialkala.data.model.requests.home

import com.google.gson.annotations.SerializedName
data class Home_slider (

	@SerializedName("id") val id : Int,
	@SerializedName("title") val title : String,
	@SerializedName("photo_id") val photo_id : Int,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String,
	@SerializedName("slider_photo") val slider_photo : String,
	@SerializedName("photo") val photo : Photo
)