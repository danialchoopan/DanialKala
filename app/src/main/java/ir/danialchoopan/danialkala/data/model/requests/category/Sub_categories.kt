package ir.danialchoopan.danialkala.data.model.requests.category
import com.google.gson.annotations.SerializedName


data class Sub_categories (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("photo_id") val photo_id : Int,
	@SerializedName("category_id") val category_id : Int,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String,
	@SerializedName("sub_category_photo") val sub_category_photo : String,
	@SerializedName("photo") val photo : Photo
)