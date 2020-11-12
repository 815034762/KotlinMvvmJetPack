package com.zs.zs_jetpack.ui.v2ex

import android.os.Bundle
import androidx.lifecycle.Observer
import com.zs.base_library.base.BaseVmFragment
import com.zs.base_library.base.DataBindingConfig
import com.zs.base_library.common.clickNoRepeat
import com.zs.base_library.common.setNoRepeatClick
import com.zs.base_library.common.smartDismiss
import com.zs.zs_jetpack.BR
import com.zs.zs_jetpack.R
import kotlinx.android.synthetic.main.fragment_integral.*
import kotlinx.android.synthetic.main.fragment_v2ex.*
import kotlinx.android.synthetic.main.fragment_v2ex.ivBack

/**
 * V2exFragment
 */
class V2exFragment : BaseVmFragment() {

    private lateinit var v2exVm: V2exVM
    private val adapter by lazy {
        V2exAdapter(mActivity)
    }

    override fun initViewModel() {
        v2exVm = getFragmentViewModel(V2exVM::class.java)
    }

    /**
     * 初始化入口
     */
    override fun init(savedInstanceState: Bundle?) {
        v2exVm.getTopList()
        v2exVm.mV2exLiveData.observe(viewLifecycleOwner, Observer {
            smartDismiss(smartRefreshLayout)
            adapter.submitList(it)
        })

        adapter.apply {
            v2ExRecyclerView.adapter = this
            setOnItemClickListener { i, view ->
                nav().navigate(
                    R.id.action_v2ex_fragment_to_web_fragment,
                    this@V2exFragment.adapter.getBundle(i)
                )
            }
        }

        smartRefreshLayout.setOnRefreshListener {
            v2exVm.getTopList()
        }

        smartRefreshLayout.setOnLoadMoreListener {
            smartDismiss(smartRefreshLayout)
        }

        ivBack.clickNoRepeat {
            nav().navigateUp()
        }
    }

    override fun loadData() {
        smartRefreshLayout.autoRefresh()
    }

    /**
     * 获取layout布局
     */
    override fun getLayoutId() = R.layout.fragment_v2ex


    /**
     * 获取dataBinding配置项
     */
    override fun getDataBindingConfig(): DataBindingConfig? {
        return DataBindingConfig(R.layout.fragment_v2ex, v2exVm).addBindingParam(BR.vm, v2exVm)
    }

    override fun onClick() {
        setNoRepeatClick(btnLogin) {
            v2exVm.getTopList()
        }
    }

}