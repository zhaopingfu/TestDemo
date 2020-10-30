package com.zhaopf.testhilt.di

import com.zhaopf.testhilt.navigator.AppNavigator
import com.zhaopf.testhilt.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * @author zhaopingfu
 * @date 2020/10/23
 */
@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModel {

    @Binds
    abstract fun bind(impl: AppNavigatorImpl): AppNavigator
}