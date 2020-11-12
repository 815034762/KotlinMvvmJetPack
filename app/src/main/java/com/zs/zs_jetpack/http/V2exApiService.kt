package com.zs.zs_jetpack.http

import com.zs.wanandroid.entity.*
import com.zs.zs_jetpack.bean.ArticleBean
import com.zs.zs_jetpack.bean.NavigationEntity
import com.zs.zs_jetpack.ui.collect.CollectBean
import com.zs.zs_jetpack.ui.integral.IntegralRecordBean
import com.zs.zs_jetpack.ui.main.home.BannerBean
import com.zs.zs_jetpack.ui.main.mine.IntegralBean
import com.zs.zs_jetpack.ui.rank.RankBean
import com.zs.zs_jetpack.ui.main.square.system.SystemBean
import com.zs.zs_jetpack.ui.main.tab.TabBean
import com.zs.zs_jetpack.ui.v2ex.V2exBeanItem
import retrofit2.http.*

/**
 * @date 2020/5/9
 * @author zs
 */
interface V2exApiService {
    /**
     * V2ex接口
     */
    @GET("/api/topics/latest.json")
    suspend fun getV2exTopicList(): MutableList<V2exBeanItem>
}