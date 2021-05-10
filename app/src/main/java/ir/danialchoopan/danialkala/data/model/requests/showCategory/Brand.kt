package ir.danialchoopan.danialkala.data.model.requests.showCategory
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Brand (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("photo_id") val photo_id : Int,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String
): Parcelable