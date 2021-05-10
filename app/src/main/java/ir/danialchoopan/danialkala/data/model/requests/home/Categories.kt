package ir.danialchoopan.danialkala.data.model.requests.home

import com.google.gson.annotations.SerializedName


data class Categories(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("photo_id") val photo_id: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("category_photo") val category_photo: String,
    @SerializedName("photo") val photo: Photo
)