package ir.danialchoopan.danialkala.data.model.requests.showCategory

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Productphotos(

    @SerializedName("id") val id: Int,
    @SerializedName("product_id") val product_id: Int,
    @SerializedName("path") val path: String,
    @SerializedName("thumbnail") val thumbnail: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String
) : Parcelable