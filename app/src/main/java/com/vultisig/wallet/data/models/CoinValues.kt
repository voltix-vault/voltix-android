package com.vultisig.wallet.data.models

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

internal data class TokenValue(
    val value: BigInteger,
    val decimals: Int,
) {
    val balance: BigDecimal
        get() = BigDecimal(value)
            .divide(BigDecimal(10).pow(decimals))
            .setScale(2, RoundingMode.HALF_UP)

}

internal data class FiatValue(
    val value: BigDecimal,
    val currency: String,
)