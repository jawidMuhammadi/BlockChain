package com.klizos.blockchain.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.klizos.blockchain.data.model.ConnectionState
import com.klizos.blockchain.data.model.transaction.Transaction
import com.klizos.blockchain.data.network.CONNECTION_URL
import com.klizos.blockchain.data.network.UNCOFIRMED_SUBCRIPTION
import com.klizos.blockchain.utils.getBitcoinPrice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Created by Ahmad Jawid Muhammadi
 * on 15-07-2021.
 */

@HiltViewModel
class FirstFragmentViewModel @Inject constructor() : ViewModel() {

    private var _connectionState = MutableLiveData<ConnectionState>()
    val connectionState: LiveData<ConnectionState> = _connectionState

    private var _transaction = MutableLiveData<Transaction>()
    val transaction: LiveData<Transaction> = _transaction


    private val writeExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    private val listener = object : WebSocketListener() {

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            updateConnectionState(ConnectionState.DISCONNECTED)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            updateConnectionState(ConnectionState.DISCONNECTED)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            updateConnectionState(ConnectionState.DISCONNECTED)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            val transaction = Gson().fromJson(text, Transaction::class.java)
            Log.d("onMessage", Gson().toJson(transaction))
            addTransactionToQueue(transaction)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            writeExecutor.execute {
                try {
                    webSocket.send(UNCOFIRMED_SUBCRIPTION)
                } catch (e: IOException) {
                    System.err.println("Unable to send messages: " + e.message)
                }
            }
            updateConnectionState(ConnectionState.CONNECTED)
        }
    }


    fun starConnection() {
        updateConnectionState(ConnectionState.CONNECTING)
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(0, TimeUnit.MILLISECONDS)
            .build()

        val request: Request = Request.Builder()
            .url(CONNECTION_URL)
            .build()
        client.newWebSocket(request, listener)
        client.dispatcher().executorService().shutdown()
    }

    private fun updateConnectionState(connectionState: ConnectionState) {
        CoroutineScope(Dispatchers.Main).launch {
            _connectionState.value = connectionState
        }
    }

    /**
     *Maintain a queue containing the 5 latest unconfirmed transactions with ‘value’ greater than $100.
     * @param transaction
     */
    private fun addTransactionToQueue(transaction: Transaction) {
        viewModelScope.launch {
            val satoshi = transaction.x?.out?.get(0)?.value
            satoshi?.let {
                val transactionValue = getBitcoinPrice(it)
                Log.d("transaction value", transactionValue.toString())
                if (transactionValue > 100.00) {
                    _transaction.value = transaction
                }
            }
        }
    }
}