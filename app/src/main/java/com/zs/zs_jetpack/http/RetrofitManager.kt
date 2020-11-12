package com.zs.zs_jetpack.http

import android.util.Log
import com.zs.zs_jetpack.constants.ApiConstants


/**
 * des 初始化Retrofit,维护多个ApiService单例对象
 *
 * @author zs
 * @date 2020-05-09
 */
class RetrofitManager {

    companion object {
        /**
         * 用于存储ApiService
         */
        private val map = mutableMapOf<Class<*>, Any>()

        /**
         * 只初始化一次
         */
        private var retrofit = RetrofitFactory.factory()

        // 可以指定域名
        fun <T : Any> getApiService(
            apiClass: Class<T>,
            url: String = "https://www.wanandroid.com"
        ): T {
            return getService(apiClass, url)
        }

        //动态指定域名
        fun <T : Any> getResApiService(apiClass: Class<T>): T {
            return getService(apiClass, "")
        }

        /**
         * 获取ApiService单例对象
         */
        private fun <T : Any> getService(apiClass: Class<T>, url: String): T {
            //重入锁单例避免多线程安全问题
            return if (map[apiClass] == null) {
                synchronized(RetrofitManager::class.java) {
                    val t: Any
                    if (url == ApiConstants.BASE_URL) { // 默认的url
                        Log.e("tag","------进入默认的url------")
                        t = retrofit.create(apiClass)
                        if (map[apiClass] == null) {
                            map[apiClass] = t
                        }
                        t
                    } else { // 动态的url
                        Log.e("tag","------获取动态的url------")
                        retrofit = RetrofitFactory.factory(url)
                        t = retrofit.create(apiClass)
                        if (map[apiClass] == null) {
                            map[apiClass] = t
                        }
                        t
                    }
                }
            } else {
                map[apiClass] as T
            }
        }
    }
}