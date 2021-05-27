package ir.danialchoopan.danialkala.adapter.spinner

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.model.requests.userAddress.UserAddressDataModelItem
import kotlinx.android.synthetic.main.item_spinner_address.view.*

class UserAddressSpinnerAdapter(
    val m_context: Context,
    val ar_data: List<UserAddressDataModelItem>
) : BaseAdapter() {
    override fun getCount(): Int = ar_data.size

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(m_context).inflate(R.layout.item_spinner_address, null)
        view.item_user_address_state.text = "استان:" + ar_data[position].state_name
        view.item_user_address_city.text = "شهر:" + ar_data[position].city_name
        view.item_user_address_address.text = "آدرس:" + ar_data[position].address
        view.item_user_address_post_code.text = "کدپستی:" + ar_data[position].post_code
        view.item_user_address_phone.text = "تلفن" + ar_data[position].lanline_phone
        view.item_user_address_telphone.text = "شماره همراه" + ar_data[position].addess_phone
        return view
    }

}