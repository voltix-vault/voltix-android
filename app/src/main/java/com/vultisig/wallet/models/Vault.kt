package com.vultisig.wallet.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Vault(
    var name: String,
    var pubKeyECDSA: String = "",
    var pubKeyEDDSA: String = "",
    var createdAt: Date = Date(),
    var hexChainCode: String = "",
    var localPartyID: String = "",
    var signers: List<String> = listOf(),
    var resharePrefix: String = "",
    var keyshares: List<KeyShare> = listOf(),
    val coins: List<Coin> = emptyList(),
) : Parcelable
