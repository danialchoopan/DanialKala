package ir.danialchoopan.danialkala.adapter.spinner

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.model.requests.stetes.StatesRequestDataModel
import ir.danialchoopan.danialkala.data.model.requests.stetes.StatesRequestDataModelItem

class AdapterCitySpinner(
    val m_context: Context,
    val ar_data: StatesRequestDataModel
) : ArrayAdapter<StatesRequestDataModelItem>(
    m_context, R.layout.item_spinner_city_state
) {
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val state_item = ar_data[position]
        val viewItem =
            LayoutInflater.from(m_context).inflate(R.layout.item_spinner_city_state, parent, false)
        val tv_spinenr1 = viewItem.findViewById<TextView>(R.id.tv_item_spinner)
        tv_spinenr1.text = state_item.name
        return super.getView(position, convertView, parent)
    }
}