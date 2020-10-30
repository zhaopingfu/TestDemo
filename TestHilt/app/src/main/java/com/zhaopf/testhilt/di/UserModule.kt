package com.zhaopf.testhilt.di

import com.zhaopf.testhilt.data.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

/**
 * @author zhaopingfu
 * @date 2020/10/30
 */
@InstallIn(ActivityComponent::class)
@Module
object UserModule {

    @ActivityScoped
    @Provides
    fun user(): User {
        return User("admin", System.currentTimeMillis())
    }
}
