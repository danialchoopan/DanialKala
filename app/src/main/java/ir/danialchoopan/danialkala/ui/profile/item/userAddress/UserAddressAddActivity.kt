package ir.danialchoopan.danialkala.ui.profile.item.userAddress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.adapter.spinner.AdapterCitySpinner
import ir.danialchoopan.danialkala.data.api.volleyRequestes.UserAddressVolleyRequest
import ir.danialchoopan.danialkala.data.api.volleyRequestes.state.ShowStatesVolleyRequest
import ir.danialchoopan.danialkala.data.model.requests.stetes.StatesRequestDataModel
import ir.danialchoopan.danialkala.data.model.requests.stetes.StatesRequestDataModelItem
import ir.danialchoopan.danialkala.databinding.DialogLoadingBinding
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import kotlinx.android.synthetic.main.activity_user_address_add.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class UserAddressAddActivity : AppCompatActivity() {
    lateinit var ar_statesRequestDataModel: StatesRequestDataModel
    lateinit var ar_city_statesRequestDataModel: StatesRequestDataModel
    var dataReceived = false
    var address_city_id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_address_add)
        //setup toolbat
        toolbar_auth_title.text = "افزودن آدرس"
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        //get states and cities form api
        val showStatesVolleyRequest = ShowStatesVolleyRequest(this@UserAddressAddActivity)
        showStatesVolleyRequest.getStates { success, statesRequestDataModel ->
            if (success) {
                ar_statesRequestDataModel = statesRequestDataModel
                val ar_statesRequestDataModel_array_list = ArrayList<String>()
                ar_statesRequestDataModel.forEach { itemState ->
                    ar_statesRequestDataModel_array_list.add(
                        itemState.name
                    )
                }

                address_state_spinner.adapter = ArrayAdapter(
                    this@UserAddressAddActivity,
                    R.layout.support_simple_spinner_dropdown_item,
                    ar_statesRequestDataModel_array_list
                )
                address_state_spinner.prompt = "استان مورد نطر خود را انتخاب کنید"
            } else {
                Toast.makeText(
                    this@UserAddressAddActivity,
                    resources.getString(R.string.errorConnectionSnackBar),
                    Toast.LENGTH_SHORT
                ).show()
            }//end if
        }//end get states
        address_state_spinner.prompt = "استان مورد نظر خود را امتحان کنید"
        address_state_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val sub_id = ar_statesRequestDataModel[position].idcity.toString()
                showStatesVolleyRequest.getCities(sub_id) { success, statesRequestDataModel ->
                    if (success) {
                        dataReceived = true
                        ar_city_statesRequestDataModel = statesRequestDataModel
                        val ar_city_statesRequestDataModel_array_list = ArrayList<String>()
                        ar_city_statesRequestDataModel.forEach { itemState ->
                            ar_city_statesRequestDataModel_array_list.add(
                                itemState.name
                            )
                        }
                        address_city_spinner.adapter = ArrayAdapter(
                            this@UserAddressAddActivity,
                            R.layout.support_simple_spinner_dropdown_item,
                            ar_city_statesRequestDataModel_array_list
                        )
                    } else {
                        Toast.makeText(
                            this@UserAddressAddActivity,
                            resources.getString(R.string.errorConnectionSnackBar),
                            Toast.LENGTH_SHORT
                        ).show()
                    }//end if
                }//end get states

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }//end on item selected listener states
        address_city_spinner.prompt = "شهر مورد نظر خود را امتحان کنید"
        address_city_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (dataReceived) {
                    address_city_id = ar_city_statesRequestDataModel[position].idcity
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }//end on item selected listener states

        //send data

        btn_add_address.setOnClickListener {
            val state_name = address_state_spinner.selectedItem.toString()
            val city_name = address_city_spinner.selectedItem.toString()
            val city_code = address_city_id.toString()
            val lanline = layoutEtxt_lanline_phone.editText!!.text.toString()
            val post_code = layoutEtxt_post_code.editText!!.text.toString()
            val addess_phone = layoutEtxt_address_phone.editText!!.text.toString()
            val addess_text = layoutEtxt_old_address_text.editText!!.text.toString()
            if (validateAddressInput()) {
                val loadingProcessDialog =
                    LoadingProcessDialog(this@UserAddressAddActivity).create()
                loadingProcessDialog.show()
                UserAddressVolleyRequest(this@UserAddressAddActivity).addUserAddress(
                    state_name, city_name, city_code, post_code, lanline, addess_phone, addess_text
                ) { success ->
                    loadingProcessDialog.dismiss()
                    if (success) {
                        finish()
                        Toast.makeText(
                            this@UserAddressAddActivity,
                            "آدرس شما با موفقیت افزودن شد",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@UserAddressAddActivity,
                            "مشکلی بیش آمده است لطفا بعدا امتحان کنید",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    this@UserAddressAddActivity,
                    "لطفا فید های ضروری را پر کنید",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }//end on create

    private fun validateAddressInput(): Boolean {
        val post_code = layoutEtxt_post_code.editText!!.text.toString()
        val lanline = layoutEtxt_lanline_phone.editText!!.text.toString()
        val addess_phone = layoutEtxt_address_phone.editText!!.text.toString()
        val addess_text = layoutEtxt_old_address_text.editText!!.text.toString()
        if (post_code.isEmpty()) {
            return false
        }
        if (lanline.isEmpty()) {
            return false
        }
        if (addess_phone.isEmpty()) {
            return false
        }
        if (addess_text.isEmpty()) {
            return false
        }
        return true
    }

}