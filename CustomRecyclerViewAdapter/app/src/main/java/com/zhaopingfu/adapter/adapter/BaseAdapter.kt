package com.zhaopingfu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import java.io.Serializable

/**
 *
 * @author zhaopingfu
 * @date 2021-03-08 15:52
 */
class BaseAdapter<T : BaseBean, VB : ViewBinding> :
    RecyclerView.Adapter<BindingViewHolder<VB>>() {

    private val dataList: MutableList<T> by lazy { mutableListOf() }
    private val holderMap: MutableMap<Int, BaseTemplate<T, VB>> by lazy { mutableMapOf() }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<VB> {
        val data = dataList[viewType]
        val template = holderMap[data.getViewType()]
        return BindingViewHolder(template?.getViewBinding(parent)!!)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<VB>, position: Int) {
        val bean = dataList[position]
        holderMap[bean.getViewType()]?.bindData(holder, bean, position)
    }

    override fun getItemCount(): Int = dataList.size

    override fun getItemViewType(position: Int): Int {
        return dataList[position].getViewType()
    }

    fun addHolder(type: Int, holder: BaseTemplate<T, VB>) {
        holderMap[type] = holder
    }

    fun addData(dataList: List<T>) {
        this.dataList.addAll(dataList)
    }
}

abstract class BaseTemplate<out T : BaseBean, out VB : ViewBinding> {
    abstract fun bindData(
        holder: BindingViewHolder<VB>,
        bean: @UnsafeVariance T,
        position: Int
    )

    abstract fun getViewBinding(parent: ViewGroup): VB
}

abstract class BaseBean : Serializable {
    abstract fun getViewType(): Int
}

/**
 * ViewHolder binding
 */
class BindingViewHolder<in VB : ViewBinding>(val binding: @UnsafeVariance VB) :
    RecyclerView.ViewHolder(binding.root)

inline fun <reified T : ViewBinding> newBindingViewHolder(parent: ViewGroup): BindingViewHolder<T> {
    val method = T::class.java.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )
    val binding = method.invoke(null, LayoutInflater.from(parent.context), parent, false) as T
    return BindingViewHolder(binding)
}

inline fun <reified T : ViewBinding> newBinding(parent: ViewGroup): T {
    val method = T::class.java.getMethod(
        "inflate",
        LayoutInflater::class.java,
        ViewGroup::class.java,
        Boolean::class.java
    )
    return method.invoke(null, LayoutInflater.from(parent.context), parent, false) as T
}
