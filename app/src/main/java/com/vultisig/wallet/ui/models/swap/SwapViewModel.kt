package com.vultisig.wallet.ui.models.swap

import androidx.lifecycle.ViewModel
import com.vultisig.wallet.ui.models.AddressProvider
import com.vultisig.wallet.ui.navigation.Navigator
import com.vultisig.wallet.ui.navigation.SendDst
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class SwapViewModel @Inject constructor(
    sendNavigator: Navigator<SendDst>,
    val addressProvider: AddressProvider
) : ViewModel() {

    val dst = sendNavigator.destination

}