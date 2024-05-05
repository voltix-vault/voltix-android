package com.vultisig.wallet.tss

import android.util.Log
import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import com.google.gson.Gson
import com.vultisig.wallet.common.Decrypt
import com.vultisig.wallet.mediator.Message
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import okhttp3.Callback
import okhttp3.OkHttpClient
import java.io.IOException

class TssMessagePuller(
    private val service: tss.ServiceImpl,
    private val hexEncryptionKey: String,
    private val serverAddress: String,
    private val localPartyKey: String,
    private val sessionID: String,
) {
    private val serverURL = "$serverAddress/message/$sessionID/$localPartyKey"
    private var job: Job? = null
    private val cache: Cache<String, Any> = CacheBuilder.newBuilder()
        .maximumSize(1000)
        .build()

    // start pulling messages from the server
    @OptIn(DelicateCoroutinesApi::class)
    fun pullMessages(messageID: String?) {
        this.job = GlobalScope.launch {
            while (isActive) {
                getMessagesFromServer(messageID)
                Thread.sleep(1000)
            }
        }
    }

    private fun getMessagesFromServer(messageID: String?) {
        try {
            val client = OkHttpClient
                .Builder()
                .retryOnConnectionFailure(true)
                .build()
            val request = okhttp3.Request.Builder()
                .url(serverURL)
                .get()
                .addHeader("Content-Type", "application/json")
                .build()

            client.newCall(request).execute().use { response ->
                if (response.code == 200) {
                    response.body?.let {
                        val messages = Gson().fromJson(it.string(), Array<Message>::class.java)
                        for (msg in messages.sortedBy { it.sequenceNo }) {
                            val key = messageID?.let { "$sessionID-$localPartyKey-${msg.hash}" }
                                ?: run { "$sessionID-$localPartyKey-$messageID-${msg.hash}" }
                            // when the message is already in the cache, skip it
                            if (cache.getIfPresent(key) != null) {
                                Log.d("TssMessagePuller", "skip message: $key, applied already")
                                continue
                            }
                            cache.put(key, msg)
                            val decryptedBody = msg.body.Decrypt(hexEncryptionKey)
                            Log.d(
                                "TssMessagePuller",
                                "apply message to TSS: hash: ${msg.hash}, messageID: $key"
                            )
                            this.service.applyData(decryptedBody)
                            deleteMessageFromServer(msg.hash, messageID)
                        }
                    }
                } else {
                    Log.e(
                        "TssMessagePuller",
                        "fail to get messages from server,${response.message} , ${response.code}"
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("TssMessagePuller", "fail to get messages from server: ${e.stackTraceToString()}")
        }
    }

    fun deleteMessageFromServer(msgHash: String, messageID: String?) {
        val client = OkHttpClient
            .Builder()
            .retryOnConnectionFailure(true)
            .build()
        val urlString = "$serverAddress/message/$sessionID/$localPartyKey/$msgHash"
        val request = okhttp3.Request.Builder()
            .url(urlString)
            .delete()
        messageID?.let {
            request.addHeader("message_id", it)
        }
        client.newCall(request.build()).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e("TssMessagePuller", "fail to delete message: ${e.stackTraceToString()}")
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if (response.code == 200) {
                    Log.d("TssMessagePuller", "delete message success")
                }
            }
        })
    }

    fun stop() {
        this.job?.cancel()
    }
}