package com.zs.zs_jetpack.ui.main.mine


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.base.LazyVmFragment
import com.zs.base_library.common.setNoRepeatClick
import com.zs.base_library.common.toast
import com.zs.base_library.utils.PrefUtils
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.constants.Constants
import com.zs.zs_jetpack.constants.UrlConstants
import com.zs.zs_jetpack.event.LoginEvent
import com.zs.zs_jetpack.event.LogoutEvent
import com.zs.zs_jetpack.utils.CacheUtil
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.system.measureTimeMillis


/**
 * des 我的
 * @author zs
 * @date 2020-05-14
 */
class MineFragment : LazyVmFragment() {

    var job: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        EventBus.getDefault().register(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    /**
     * 显示消息
     */
    private fun showMessage(message: String) {
        tvText.text = message
    }

    private suspend fun getMessageFromNetwork(text: String, delayTime: Int): String {
        Log.e("coroutines", "=======执行getMessageFromNetWork=======" + delayTime)
        var name = ""
        // suspend里面调用的方法，也是被编译器用suspend关键字修饰的
        withContext(Dispatchers.IO) {
            for (i in 1..delayTime) {
                // 这里模拟一个耗时操作
                Thread.sleep(500)
                Log.e("coroutines","${delayTime}-----------------------${i}")
            }
        }
        name = text
        return name
    }

    private suspend fun intValue1():Int {
        delay(1000)
        return 1
    }

    private suspend fun intValue2():Int {
        delay(2000)
        return 2
    }


    /**
     * 用户积分信息
     */
    private var integralBean: IntegralBean? = null

    private var mineVM: MineVM? = null

    override fun initViewModel() {
        mineVM = getFragmentViewModel(MineVM::class.java)

        val elapsedTime = measureTimeMillis {
//            val value1 = intValue1()
//            val value2 = intValue2()
//            println("the result is ${value1 + value2}")
        }

//        job = GlobalScope.launch(Dispatchers.Main) {
//            // 执行这一句之后，launch之后就只能执行一次
////            job?.cancel()
//            // 下面方法都是顺序执行的
//            var name = getMessageFromNetwork("Name1", 100)
//            showMessage(name)
//            Log.e("coroutines", "=======Name1=======" + name)
//            var name1 = getMessageFromNetwork("Name2", 10000)
//            showMessage(name1)
//            Log.e("coroutines", "=======Name2=======" + name1)
//            var name2 = getMessageFromNetwork("Name3", 10)
//            showMessage(name2)
//            Log.e("coroutines", "=======name2=======" + name2)
//        }
    }

    override fun observe() {
        mineVM?.internalLiveData?.observe(this, Observer {
            integralBean = it
            setIntegral()
        })
    }

    private fun setIntegral() {
        //通过dataBinDing与View绑定
        mineVM?.username?.set(integralBean?.username)
        mineVM?.id?.set("${integralBean?.userId}")
        mineVM?.rank?.set("${integralBean?.rank}")
        mineVM?.internal?.set("${integralBean?.coinCount}")
    }

    override fun lazyInit() {
        //先判断数据是否为空，然后再强转，否则会出异常
        PrefUtils.getObject(Constants.INTEGRAL_INFO)?.let {
            //先从本地获取积分，获取不到再通过网络获取
            integralBean = it as IntegralBean?
        }
        if (integralBean == null) {
            if (CacheUtil.isLogin()) {
                mineVM?.getFlowInternal()
            }
        } else {
            setIntegral()
        }
    }


    override fun getLayoutId() = R.layout.fragment_mine

    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_mine, mineVM)
            .addBindingParam(BR.vm, mineVM)
    }

    override fun onClick() {
        setNoRepeatClick(
            ivHead,
            tvName,
            tvId,
            llHistory,
            llRanking,
            clIntegral,
            clCollect,
            clArticle,
            clWebsite,
            clSet,
            clV2ex
        ) {
            when (it.id) {
                //头像
                R.id.ivHead -> toast("我只是一只睡着的小老鼠...")
                //用户名
                R.id.tvName -> {
                    if (!CacheUtil.isLogin()) {
                        nav().navigate(R.id.action_main_fragment_to_login_fragment)
                    }
                }
                //历史
                R.id.llHistory -> nav().navigate(R.id.action_main_fragment_to_history_fragment)
                //排名
                R.id.llRanking -> {
                    if (CacheUtil.isLogin()) {
                        nav().navigate(R.id.action_main_fragment_to_rank_fragment, Bundle().apply {
                            integralBean?.apply {
                                putInt(Constants.MY_INTEGRAL, coinCount)
                                putInt(Constants.MY_RANK, rank)
                                putString(Constants.MY_NAME, username)
                            }
                        })
                    } else {
                        toast("请先登录")
                    }
                }
                //积分
                R.id.clIntegral -> {
                    if (CacheUtil.isLogin()) {
                        nav().navigate(R.id.action_main_fragment_to_integral_fragment)
                    } else {
                        toast("请先登录")
                    }
                }
                //我的收藏
                R.id.clCollect -> {
                    if (CacheUtil.isLogin()) {
                        nav().navigate(R.id.action_main_fragment_to_collect_fragment)
                    } else {
                        toast("请先登录")
                    }
                }
                //我的文章
                R.id.clArticle -> {
                    if (CacheUtil.isLogin()) {
                        nav().navigate(R.id.action_main_fragment_to_my_article_fragment)
                    } else {
                        toast("请先登录")
                    }
                }
                //官网
                R.id.clWebsite -> {
                    nav().navigate(R.id.action_main_fragment_to_web_fragment, Bundle().apply {
                        putString(Constants.WEB_URL, UrlConstants.WEBSITE)
                        putString(Constants.WEB_TITLE, Constants.APP_NAME)
                    })
                }
                R.id.clSet -> {
                    nav().navigate(R.id.action_main_fragment_to_set_fragment)
                }
                R.id.clV2ex -> {
                    nav().navigate(R.id.action_main_fragment_to_v2ex_fragment)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    /**
     * 登陆消息,收到消息请求个人信息接口
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun loginEvent(loginEvent: LoginEvent) {
        mineVM?.getFlowInternal()
    }

    /**
     * 退出消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun logoutEvent(loginEvent: LogoutEvent) {
        mineVM?.username?.set("请先登录")
        mineVM?.id?.set("---")
        mineVM?.rank?.set("0")
        mineVM?.internal?.set("0")
    }

    override fun onDestroy() {
        super.onDestroy()
        // 取消协程
        job?.cancel()
    }
}
