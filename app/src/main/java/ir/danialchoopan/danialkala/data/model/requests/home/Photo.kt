package ir.danialchoopan.danialkala.data.model.requests.home

import com.google.gson.annotations.SerializedName

data class Photo (

	@SerializedName("id") val id : Int,
	@SerializedName("path") val path : String,
	@SerializedName("created_at") val created_at : String,
	@SerializedName("updated_at") val updated_at : String
)