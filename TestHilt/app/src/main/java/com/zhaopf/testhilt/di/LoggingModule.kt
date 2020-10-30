package com.zhaopf.testhilt.di

import com.zhaopf.testhilt.data.LoggerDataSource
import com.zhaopf.testhilt.data.LoggerInLocalDataSource
import com.zhaopf.testhilt.data.LoggerInMemoryDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * @author zhaopingfu
 * @date 2020/10/29
 */

@Qualifier
annotation class InMemoryLogger

@Qualifier
annotation class DatabaseLogger


@InstallIn(ActivityComponent::class)
@Module
abstract class LoggingMemoryModule {

    @InMemoryLogger
    @ActivityScoped
    @Binds
    abstract fun bindInMemoryLogger(impl: LoggerInMemoryDataSource): LoggerDataSource
}

@InstallIn(ApplicationComponent::class)
@Module
abstract class LoggingDatabaseModule {

    @DatabaseLogger
    @Singleton
    @Binds
    abstract fun bindDatabaseLogger(impl: LoggerInLocalDataSource): LoggerDataSource
}
