package com.zs.zs_jetpack.ui.v2ex

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zs.base_library.common.clickNoRepeat
import com.zs.zs_jetpack.R
import com.zs.zs_jetpack.common.getDefaultDiff
import com.zs.zs_jetpack.databinding.ItemV2exBinding

/**
 * @author ZTY
 * @date 2020-11-12
 */
class V2exAdapter(private val context: Context) :
    ListAdapter<V2exBeanItem, RecyclerView.ViewHolder>(
        getDefaultDiff()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ItemV2exBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_v2ex,
            parent,
            false
        )
        return V2exAdapter.V2exViewHolder(binding.root)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = DataBindingUtil.getBinding<ItemV2exBinding>(holder.itemView)?.apply {
            dataBean = getItem(position)
        }

        holder.itemView.clickNoRepeat {
            onItemClickListener?.let {
                it.invoke(position,holder.itemView)
            }
        }

        binding?.executePendingBindings()
    }

    /**
     * 默认viewHolder
     */
    class V2exViewHolder constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
    }

    private var onItemClickListener: ((Int,View)->Unit)? = null

    /**
     * 注册item点击事件
     */
    fun setOnItemClickListener(onItemClickListener: ((Int, View) -> Unit)? = null) {
        this.onItemClickListener = onItemClickListener
    }

    /**
     * 获取跳转至web界面的bundle
     */
    fun getBundle(position: Int): Bundle {
        return Bundle().apply {
            putString("loadUrl", currentList[position].url)
            putString("title", currentList[position].title)
            putString("author", currentList[position].member.username)
            putInt("id", currentList[position].id)
        }
    }
}