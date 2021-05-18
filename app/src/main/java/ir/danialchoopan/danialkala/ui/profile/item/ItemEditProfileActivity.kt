package ir.danialchoopan.danialkala.ui.profile.item

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import ir.danialchoopan.danialkala.R
import ir.danialchoopan.danialkala.data.UserSharePreferences
import ir.danialchoopan.danialkala.data.api.volleyRequestes.auth.AuthUserVolleyRequest
import ir.danialchoopan.danialkala.data.api.volleyRequestes.state.ShowStatesVolleyRequest
import ir.danialchoopan.danialkala.data.model.requests.stetes.StatesRequestDataModel
import ir.danialchoopan.danialkala.dialog.LoadingProcessDialog
import ir.danialchoopan.danialkala.utails.UpdateUserInfoShareInfo
import kotlinx.android.synthetic.main.activity_item_edit_profile.*
import kotlinx.android.synthetic.main.activity_user_address_add.*
import kotlinx.android.synthetic.main.activity_user_address_update.*
import kotlinx.android.synthetic.main.toolbar_auth_user_activities.*

class ItemEditProfileActivity : AppCompatActivity() {
    lateinit var ar_statesRequestDataModel: StatesRequestDataModel
    lateinit var ar_city_statesRequestDataModel: StatesRequestDataModel
    var dataReceived = false
    var address_city_id = 0
    var address_city_enable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //update user info
        UpdateUserInfoShareInfo(this@ItemEditProfileActivity).instance()
        //end update user info
        setContentView(R.layout.activity_item_edit_profile)
        val loadingProcessDialog_loadUserInfo = LoadingProcessDialog(this).create()
        //toolbar
        toolbar_auth_title.text = "ویرایش اطلاعات کاربری"
        toolbar_auth_close.setOnClickListener {
            finish()
        }
        //end toolbar
        loadingProcessDialog_loadUserInfo.show()
        //get user info form api
        val authUserVolleyRequest = AuthUserVolleyRequest(this@ItemEditProfileActivity)
        authUserVolleyRequest.getUserData { editProfileDataModelRequest ->
            loadingProcessDialog_loadUserInfo.dismiss()
            edit_profile_layoutEtxt_name.editText!!.setText(editProfileDataModelRequest.user.name)
            edit_profile_layoutEtxt_phone.editText!!.setText(editProfileDataModelRequest.user.phone)
            edit_profile_layoutEtxt_national_code.editText!!.setText(editProfileDataModelRequest.user.user_info.national_code)
        }

        //get states and cities form api
        val showStatesVolleyRequest = ShowStatesVolleyRequest(this@ItemEditProfileActivity)
        showStatesVolleyRequest.getStates { success, statesRequestDataModel ->
            if (success) {
                ar_statesRequestDataModel = statesRequestDataModel
                val ar_statesRequestDataModel_array_list = ArrayList<String>()
                ar_statesRequestDataModel.forEach { itemState ->
                    ar_statesRequestDataModel_array_list.add(
                        itemState.name
                    )
                }

                edit_profile_spinner_states.adapter = ArrayAdapter(
                    this@ItemEditProfileActivity,
                    R.layout.support_simple_spinner_dropdown_item,
                    ar_statesRequestDataModel_array_list
                )
                edit_profile_spinner_states.prompt = "استان مورد نطر خود را انتخاب کنید"
            } else {
                Toast.makeText(
                    this@ItemEditProfileActivity,
                    resources.getString(R.string.errorConnectionSnackBar),
                    Toast.LENGTH_SHORT
                ).show()
            }//end if
        }//end get states
        edit_profile_spinner_states.prompt = "استان مورد نظر خود را امتحان کنید"
        edit_profile_spinner_states.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
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
                            edit_profile_spinner_cities.adapter = ArrayAdapter(
                                this@ItemEditProfileActivity,
                                R.layout.support_simple_spinner_dropdown_item,
                                ar_city_statesRequestDataModel_array_list
                            )
                        } else {
                            Toast.makeText(
                                this@ItemEditProfileActivity,
                                resources.getString(R.string.errorConnectionSnackBar),
                                Toast.LENGTH_SHORT
                            ).show()
                        }//end if
                    }//end get states

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }//end on item selected listener states
        edit_profile_spinner_cities.prompt = "شهر مورد نظر خود را امتحان کنید"
        edit_profile_spinner_cities.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (dataReceived) {
                        address_city_id = ar_city_statesRequestDataModel[position].idcity
                        address_city_enable = true
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }//end on item selected listener states


        btn_save_edit_user_info.setOnClickListener {
            if (validateAddressInput()) {
                val loadingDialogUpdateUserInfo =
                    LoadingProcessDialog(this@ItemEditProfileActivity).create()
                loadingDialogUpdateUserInfo.show()
                var state_name = ""
                var city_name = ""
                if (address_city_enable) {
                    state_name = edit_profile_spinner_states.selectedItem.toString()
                    city_name = edit_profile_spinner_cities.selectedItem.toString()
                }
                val city_code = address_city_id.toString()


                val user_name = edit_profile_layoutEtxt_name.editText!!.text.toString()
                val phone = edit_profile_layoutEtxt_phone.editText!!.text.toString()
                val national_code = edit_profile_layoutEtxt_national_code.editText!!.text.toString()
                val userSharePreferences =
                    UserSharePreferences(this@ItemEditProfileActivity).sharePreferences
                authUserVolleyRequest.updateUserData(
                    user_name,
                    phone,
                    national_code,
                    state_name,
                    city_name,
                    city_code
                ) { editProfileDataModelRequest ->
                    loadingDialogUpdateUserInfo.dismiss()
                    if (editProfileDataModelRequest.success) {
                        userSharePreferences.edit().also { editor ->
                            editor.putString("name", editProfileDataModelRequest.user.name)
                            editor.putString("phone", editProfileDataModelRequest.user.phone)
                        }.apply()
                        this@ItemEditProfileActivity.finish()
                        Toast.makeText(
                            this@ItemEditProfileActivity,
                            "اطلاعات شما با موفقیت بروز شد.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@ItemEditProfileActivity,
                            "مشکلی پش آمده است لطفا بعدا دوباره امتحان کنید",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    this@ItemEditProfileActivity,
                    "لطفا فیلد های ضروری را پر کنید",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }


    private fun validateAddressInput(): Boolean {
        val name = edit_profile_layoutEtxt_name.editText!!.text.toString()
        val phone = edit_profile_layoutEtxt_phone.editText!!.text.toString()
        val national_code = edit_profile_layoutEtxt_national_code.editText!!.text.toString()
        if (name.isEmpty()) {
            return false
        }
        if (phone.isEmpty()) {
            return false
        }
        if (national_code.isEmpty()) {
            return false
        }
        return true
    }
}