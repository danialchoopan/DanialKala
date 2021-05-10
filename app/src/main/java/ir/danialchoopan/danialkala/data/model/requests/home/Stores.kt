package ir.danialchoopan.danialkala.data.model.requests.home
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stores (

	@SerializedName("id") val id : Int,
	@SerializedName("product_id") val product_id : Int,
	@SerializedName("color_id") val color_id : Int,
	@SerializedName("count") val count : Int,
	@SerializedName("warranty") val warranty : String,
	@SerializedName("price_buy") val price_buy : Int,
	@SerializedName("price_sell") val price_sell : Int,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String
):Parcelable