package com.zs.zs_jetpack.ui.v2ex

import androidx.lifecycle.MutableLiveData
import com.zs.base_library.base.BaseRepository
import com.zs.base_library.http.ApiException
import com.zs.zs_jetpack.constants.ApiConstants
import com.zs.zs_jetpack.http.RetrofitManager
import com.zs.zs_jetpack.http.V2exApiService
import kotlinx.coroutines.CoroutineScope

/**
 * @author Zty
 * @date 2020-11-12
 */
class V2exRepo(coroutineScope:CoroutineScope,errorLiveData: MutableLiveData<ApiException>) : BaseRepository(coroutineScope,errorLiveData){

     fun getLateTopic(v2exItemData:MutableLiveData<MutableList<V2exBeanItem>>) {
        launch(
            block = {
                RetrofitManager.getApiService(V2exApiService::class.java,ApiConstants.V2EX_URL).getV2exTopicList()
            },
            success = {
                //处理刷新
                v2exItemData.value.apply {
                      v2exItemData.postValue(it)
                }
            }
        )
    }
}