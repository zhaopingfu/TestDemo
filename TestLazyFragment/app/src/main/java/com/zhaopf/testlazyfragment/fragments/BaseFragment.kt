package com.zhaopf.testlazyfragment.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.ParameterizedType

/**
 * @author pingfu.zhao
 * @date 2020-01-14 10:39
 */
abstract class BaseFragment<T : ViewBinding> : Fragment() {

    private var isViewInitiated = false
    private var isVisibleToUser = false
    private var isDataInitiated = false
    protected lateinit var binding: T

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        if (!isLazyLoad) {
            if (!isDataInitiated) {
                fetchData()
                isDataInitiated = true
            }
        } else {
            prepareFetchData()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 利用反射，调用指定ViewBinding中的inflate方法填充视图
        val superclass = javaClass.genericSuperclass
        val aClass = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
        try {
            val method = aClass.getDeclaredMethod(
                "inflate",
                LayoutInflater::class.java,
                ViewGroup::class.java,
                Boolean::class.javaPrimitiveType
            )
            binding = method.invoke(null, inflater, container, false) as T
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (isUiVisible) {
            onUiShown()
        }
    }

    override fun onPause() {
        super.onPause()
        onUiHidden()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isViewInitiated = false
        isDataInitiated = false
    }

    /**
     * 多层ViewPager嵌套需要自行调用此方法传递给当前visible child fragment
     *
     * @param isVisibleToUser 是否对用户展示
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        if (isLazyLoad) {
            prepareFetchData()
        }
        if (isAdded) {
            if (isUiVisible) {
                onUiShown()
            } else {
                onUiHidden()
            }
        }
    }

    /**
     * 只会调用一次
     */
    protected abstract fun fetchData()

    /**
     * ui可见
     */
    protected abstract fun onUiShown()

    /**
     * ui隐藏
     */
    protected abstract fun onUiHidden()

    private fun prepareFetchData() {
        if (isVisibleToUser && isViewInitiated && !isDataInitiated) {
            fetchData()
            isDataInitiated = true
        }
    }

    private val isLazyLoad: Boolean
        get() = true
    private val isUiVisible: Boolean
        get() = isResumed && userVisibleHint && isParentFragVisible
    private val isParentFragVisible: Boolean
        get() {
            val tFrag = parentFragment
            val parentFrag = if (tFrag is BaseFragment<*>) tFrag else null
            return parentFrag == null || parentFrag.isUiVisible
        }
}
