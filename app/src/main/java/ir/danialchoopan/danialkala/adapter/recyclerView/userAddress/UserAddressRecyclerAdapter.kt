package ir.danialchoopan.danialkala.adapter.recyclerView.userAddress

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.api.volleyRequestes.UserAddressVolleyRequest
import ir.danialchoopan.danialkala.data.model.requests.userAddress.UserAddressDataModel
import ir.danialchoopan.danialkala.data.model.requests.userAddress.UserAddressDataModelItem
import ir.danialchoopan.danialkala.ui.profile.item.userAddress.UserAddressUpdateActivity
import kotlinx.android.synthetic.main.item_rcy_user_address.view.*

class UserAddressRecyclerAdapter(val m_context: Context) :
    RecyclerView.Adapter<UserAddressRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    var ar_data = emptyList<UserAddressDataModelItem>()
    fun setData(ar_data: ArrayList<UserAddressDataModelItem>) {
        this.ar_data = ar_data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rcy_user_address, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userAddressItem = ar_data[position]
        holder.view.item_user_address_state.text = userAddressItem.state_name
        holder.view.item_user_address_city.text = userAddressItem.city_name
        holder.view.item_user_address_address.text = userAddressItem.address
        holder.view.item_user_address_post_code.text = userAddressItem.post_code
        holder.view.item_user_address_phone.text = userAddressItem.addess_phone
        holder.view.item_user_address_telphone.text = userAddressItem.lanline_phone

        //api user address
        val userAddressVolleyRequest = UserAddressVolleyRequest(m_context)
        //img btn delete
        holder.view.item_user_address_img_delete.setOnClickListener {
            AlertDialog.Builder(m_context).also { alert ->
                alert.setTitle("حذف ایتم")
                alert.setMessage("آیا می خواهید این آدرس را حذف کنید.")
                alert.setNegativeButton("نه شوخی کردم") { _, _ ->

                }
                alert.setPositiveButton("حذف") { _, _ ->
                    userAddressVolleyRequest.deleteUserAddress(userAddressItem.id.toString()) { success ->
                        if (success) {
                            Toast.makeText(
                                m_context,
                                "آدرس مورد نظر شما با موفقیت حدف شد",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                m_context,
                                "مشکلی پیش آمده است لطفا بعدا امتحان کنید",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }//end delete user address
                }//btn positive
            }//end alert
        }//end btn img
        //img btn edit
        holder.view.item_user_address_img_edit.setOnClickListener {
            Intent(m_context, UserAddressUpdateActivity::class.java).also { intent ->
                intent.putExtra("userAddressItemId", userAddressItem.id)
                m_context.startActivity(intent)
            }//end intent
        }//end btn edit user address

    }

    override fun getItemCount(): Int = ar_data.size
}