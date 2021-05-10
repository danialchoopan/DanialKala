package ir.danialchoopan.danialkala.data.model.requests.showCategory
import com.google.gson.annotations.SerializedName

data class ShowCategoryRequestDataModel (

	@SerializedName("category") val category : Category
)