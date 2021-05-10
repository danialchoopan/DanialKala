package ir.danialchoopan.danialkala.ui.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ir.danialchoopan.danialkala.data.api.repository.CategoryRequestRepository
import ir.danialchoopan.danialkala.data.model.requests.category.Category_Base
import kotlinx.coroutines.launch

class ProductCategoryViewModel(application: Application) : AndroidViewModel(application) {
    val productCategory = MutableLiveData<List<Category_Base>>()

    fun getProductCategory() {
        viewModelScope.launch {
            productCategory.value = CategoryRequestRepository().categoryRequest()
        }
    }
}