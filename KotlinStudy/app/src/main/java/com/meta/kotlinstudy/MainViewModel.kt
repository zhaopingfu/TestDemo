package com.meta.kotlinstudy

import android.util.Log
import androidx.lifecycle.*
import com.meta.kotlinstudy.coroutine_retrofit.BannerItemBean
import com.meta.kotlinstudy.coroutine_retrofit.RetrofitClient
import kotlinx.coroutines.launch

/**
 * @author zhaopingfu
 * @date 2019-09-21 17:11
 */
class MainViewModel(private val str: String) : ViewModel() {

    private val TAG = "MainViewModel"

    val bannerList: MutableLiveData<List<BannerItemBean>> by lazy { MutableLiveData() }

    fun getBannerList() {
        viewModelScope.launch {
            val listRepos = RetrofitClient.articleService.listRepos()
            Log.d(TAG, "getBannerList: $listRepos")
            bannerList.value = listRepos.data
        }
    }
}

class MainViewModelProvider(owner: ViewModelStoreOwner, factory: Factory) :
    ViewModelProvider(owner, factory)

class MainViewModelFactory(private val str: String) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(String::class.java)
            .newInstance(str)
    }
}