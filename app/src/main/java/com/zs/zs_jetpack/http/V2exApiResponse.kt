package com.zs.zs_jetpack.http

import com.zs.base_library.http.ApiException
import java.io.Serializable

/**
 * des Api描述类，用于承载业务信息以及基础业务逻辑判断
 * @date 2020/7/5
 * @author zs
 */
class V2exApiResponse<T> : Serializable {

    private var data: T? = null


    /**
     * 如果服务端data肯定不为null，直接将data返回。
     * 假如data为null证明服务端出错,这种错误已经产生并且不可逆，
     * 客户端只需保证不闪退并给予提示即可
     */
    fun data(): T {
        return data!!
    }

}