package com.zs.zs_jetpack.ui.v2ex

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zs.base_library.base.BaseViewModel

class V2exVM : BaseViewModel() {

    /**
     * V2ex最近热点
     */
    var mV2exLiveData = MutableLiveData<MutableList<V2exBeanItem>>()

    private val repo by lazy {
        V2exRepo(viewModelScope, errorLiveData)
    }

    val userName = ObservableField<String>().apply {
        set("")
    }

    fun getTopList() {
        repo.getLateTopic(mV2exLiveData)
    }

}