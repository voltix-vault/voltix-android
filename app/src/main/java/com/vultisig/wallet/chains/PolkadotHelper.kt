package com.vultisig.wallet.chains

import com.google.protobuf.ByteString
import com.vultisig.wallet.common.Numeric
import com.vultisig.wallet.common.Utils
import com.vultisig.wallet.common.toHexByteArray
import com.vultisig.wallet.common.toHexBytesInByteString
import com.vultisig.wallet.models.Coin
import com.vultisig.wallet.models.Coins
import com.vultisig.wallet.models.SignedTransactionResult
import com.vultisig.wallet.presenter.keysign.BlockChainSpecific
import com.vultisig.wallet.presenter.keysign.KeysignPayload
import com.vultisig.wallet.tss.getSignature
import wallet.core.jni.AnyAddress
import wallet.core.jni.CoinType
import wallet.core.jni.DataVector
import wallet.core.jni.PublicKey
import wallet.core.jni.PublicKeyType
import wallet.core.jni.TransactionCompiler
import java.math.BigInteger

internal class PolkadotHelper(
    private val vaultHexPublicKey: String,
) {
    private val coinType = CoinType.POLKADOT

    companion object {
        internal val DefaultFeeInPlancks: BigInteger = 10_000_000_000.toBigInteger()
    }

    fun getCoin(): Coin? {
        val publicKey = PublicKey(vaultHexPublicKey.toHexByteArray(), PublicKeyType.ED25519)
        val address = coinType.deriveAddressFromPublicKey(publicKey)
        return Coins.getCoin("DOT", address, vaultHexPublicKey, coinType)
    }

    fun getPreSignedInputData(keysignPayload: KeysignPayload): ByteArray {
        val polkadotSpecific = keysignPayload.blockChainSpecific as? BlockChainSpecific.Polkadot
            ?: throw IllegalArgumentException("Invalid blockChainSpecific")
        val toAddress = AnyAddress(keysignPayload.toAddress, coinType)
        val transfer = wallet.core.jni.proto.Polkadot.Balance.Transfer.newBuilder()
            .setToAddress(toAddress.description())
            .setValue(ByteString.copyFrom(keysignPayload.toAmount.toByteArray()))
        if (keysignPayload.memo != null) {
            transfer.setMemo(keysignPayload.memo)
        }
        val balanceTransfer = wallet.core.jni.proto.Polkadot.Balance.newBuilder()
            .setTransfer(transfer.build()).build()

        val input = wallet.core.jni.proto.Polkadot.SigningInput.newBuilder()
            .setGenesisHash(polkadotSpecific.genesisHash.toHexBytesInByteString())
            .setBlockHash(polkadotSpecific.recentBlockHash.toHexBytesInByteString())
            .setNonce(polkadotSpecific.nonce.toLong())
            .setSpecVersion(polkadotSpecific.specVersion.toInt())
            .setNetwork(coinType.ss58Prefix())
            .setTransactionVersion(polkadotSpecific.transactionVersion.toInt())
            .setEra(
                wallet.core.jni.proto.Polkadot.Era.newBuilder()
                    .setBlockNumber(polkadotSpecific.currentBlockNumber.toLong())
                    .setPeriod(64)
                    .build()
            )
            .setBalanceCall(balanceTransfer)
            .build()
        return input.toByteArray()
    }

    fun getPreSignedImageHash(keysignPayload: KeysignPayload): List<String> {
        val result = getPreSignedInputData(keysignPayload)
        val hashes = wallet.core.jni.TransactionCompiler.preImageHashes(coinType, result)
        val preSigningOutput =
            wallet.core.jni.proto.TransactionCompiler.PreSigningOutput.parseFrom(hashes)
        return listOf(Numeric.toHexStringNoPrefix(preSigningOutput.dataHash.toByteArray()))
    }

    fun getSignedTransaction(
        keysignPayload: KeysignPayload,
        signatures: Map<String, tss.KeysignResponse>,
    ): SignedTransactionResult {
        val publicKey = PublicKey(vaultHexPublicKey.toHexByteArray(), PublicKeyType.ED25519)
        val input = getPreSignedInputData(keysignPayload)
        val hashes = TransactionCompiler.preImageHashes(coinType, input)
        val preSigningOutput =
            wallet.core.jni.proto.TransactionCompiler.PreSigningOutput.parseFrom(hashes)
        val key = Numeric.toHexStringNoPrefix(preSigningOutput.data.toByteArray())
        val allSignatures = DataVector()
        val publicKeys = DataVector()
        val signature = signatures[key]?.getSignature() ?: throw Exception("Signature not found")
        if (!publicKey.verify(signature, preSigningOutput.data.toByteArray())) {
            throw Exception("Signature verification failed")
        }
        allSignatures.add(signature)
        publicKeys.add(publicKey.data())
        val compiledWithSignature =
            TransactionCompiler.compileWithSignatures(coinType, input, allSignatures, publicKeys)
        val output = wallet.core.jni.proto.Polkadot.SigningOutput.parseFrom(compiledWithSignature)

        return SignedTransactionResult(
            rawTransaction = Numeric.toHexStringNoPrefix(
                output.encoded.toByteArray()
            ),
            transactionHash = Numeric.toHexStringNoPrefix(
                Utils.blake2bHash(
                    output.encoded.toByteArray().take(32).toByteArray()
                )
            )
        )
    }
}