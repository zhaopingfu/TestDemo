package com.zhaopf.testlazyfragment.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author pingfu.zhao
 * @date 2020-01-14 10:39
 */
public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    private boolean isViewInitiated;
    private boolean isVisibleToUser;
    private boolean isDataInitiated;

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
        mRootView = inflater.inflate(getLayoutResId(), container, false);
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
     * 获取布局id
     */
    protected abstract int getLayoutResId();

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
        BaseFragment parentFrag = tFrag instanceof BaseFragment ? (BaseFragment) tFrag : null;
        return parentFrag == null || parentFrag.isUiVisible();
    }
}