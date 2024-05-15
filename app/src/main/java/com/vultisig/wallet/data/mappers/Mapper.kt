package com.vultisig.wallet.data.mappers

interface Mapper<I, O> {
    fun map(from: I): O
}

typealias MapperFunc<I, O> = (from: I) -> O