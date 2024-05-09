package com.vultisig.wallet.service

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideTHORChainService(gson: Gson): THORChainService =
        THORChainService(gson)

}