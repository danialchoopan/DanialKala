package ir.danialchoopan.danialkala.fragments.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.danialchoopan.danialkala.data.api.repository.HomeRequestRepository
import ir.danialchoopan.danialkala.data.model.requests.home.HomeRequestModel
import kotlinx.coroutines.launch

class HomePageViewModel(application: Application) : AndroidViewModel(application) {
    val homePageData: MutableLiveData<HomeRequestModel> = MutableLiveData()

    fun getHomePageData() {
        viewModelScope.launch {
            homePageData.value = HomeRequestRepository().homeRequest()
        }
    }
}