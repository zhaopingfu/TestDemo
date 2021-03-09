package com.zhaopingfu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

/**
 *
 * @author zhaopingfu
 * @date 2021-03-08 15:52
 */
class BaseAdapter<T : BaseBean> : RecyclerView.Adapter<MViewHolder>() {

    private val dataList: MutableList<T> by lazy { mutableListOf() }
    private val holderMap: MutableMap<Int, BaseTemplate<T>> by lazy { mutableMapOf() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        val template = holderMap[viewType]
        val view = LayoutInflater.from(parent.context)
            .inflate(template?.getItemViewId() ?: 0, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        val bean = dataList[position]
        holderMap[bean.getViewType()]?.convert(holder, position, bean)
    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        return dataList[position].getViewType()
    }

    fun <RealT : T> addHolder(type: Int, holder: BaseTemplate<RealT>) {
        holderMap[type] = holder
    }

    fun addData(dataList: List<T>) {
        this.dataList.addAll(dataList)
    }
}

class MViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

abstract class BaseTemplate<out T : BaseBean> {
    abstract fun convert(holder: MViewHolder, position: Int, bean: @UnsafeVariance T)

    abstract fun getItemViewId(): Int
}

abstract class BaseBean : Serializable {
    abstract fun getViewType(): Int
}
