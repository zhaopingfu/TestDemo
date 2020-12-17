package com.zhaopf.testlazyfragment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author pingfu.zhao
 * @date 2020-01-14 10:39
 */
public abstract class BaseFragment<T extends ViewBinding> extends Fragment {

    protected View mRootView;
    private boolean isViewInitiated;
    private boolean isVisibleToUser;
    private boolean isDataInitiated;
    protected T binding;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        if (!isLazyLoad()) {
            if (!isDataInitiated) {
                fetchData();
                isDataInitiated = true;
            }
        } else {
            prepareFetchData();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 利用反射，调用指定ViewBinding中的inflate方法填充视图
        Type superclass = getClass().getGenericSuperclass();
        Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
        try {
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            binding = (T) method.invoke(null, inflater, container, false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        mRootView = binding.getRoot();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isUiVisible()) {
            onUiShown();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        onUiHidden();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewInitiated = false;
        isDataInitiated = false;
    }

    /**
     * 多层ViewPager嵌套需要自行调用此方法传递给当前visible child fragment
     *
     * @param isVisibleToUser 是否对用户展示
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isLazyLoad()) {
            prepareFetchData();
        }
        if (isAdded()) {
            if (isUiVisible()) {
                onUiShown();
            } else {
                onUiHidden();
            }
        }
    }

    /**
     * 只会调用一次
     */
    protected abstract void fetchData();

    /**
     * ui可见
     */
    protected abstract void onUiShown();

    /**
     * ui隐藏
     */
    protected abstract void onUiHidden();

    protected final void prepareFetchData() {
        if (isVisibleToUser && isViewInitiated && !isDataInitiated) {
            fetchData();
            isDataInitiated = true;
        }
    }

    protected boolean isLazyLoad() {
        return true;
    }

    protected boolean isUiVisible() {
        return isResumed() && getUserVisibleHint() && isParentFragVisible();
    }

    protected boolean isParentFragVisible() {
        Fragment tFrag = getParentFragment();
        BaseFragment<?> parentFrag = tFrag instanceof BaseFragment ? (BaseFragment<?>) tFrag : null;
        return parentFrag == null || parentFrag.isUiVisible();
    }
}
