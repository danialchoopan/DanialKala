package ir.danialchoopan.danialkala.data.model.requests.showCategory
import com.google.gson.annotations.SerializedName

data class Category (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("photo_id") val photo_id : Int,
	@SerializedName("category_id") val category_id : Int,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String,
	@SerializedName("products") val products : List<Products>
)