package com.vultisig.wallet.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class vultisigApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        WalletCoreLoader
    }
}