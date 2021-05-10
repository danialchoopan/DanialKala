package ir.danialchoopan.danialkala.data.model.requests.home

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class New_products(

    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("colors") val colors: List<Colors>,
    @SerializedName("status") val status: String,
    @SerializedName("brand_id") val brand_id: Int,
    @SerializedName("subCategory_id") val subCategory_id: Int,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("price") val price: Int,
    @SerializedName("category") val category: String,
    @SerializedName("Subcategory") val subcategory: String,
    @SerializedName("brand") val brand: Brand,
    @SerializedName("productphotos") val productphotos: List<Productphotos>,
    @SerializedName("stores") val stores: List<Stores>
) : Parcelable