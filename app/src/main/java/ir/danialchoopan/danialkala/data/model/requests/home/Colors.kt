package ir.danialchoopan.danialkala.data.model.requests.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Colors (

	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("hex_code") val hex_code : String,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String
):Parcelable