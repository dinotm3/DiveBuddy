package d.tmesaric.divebuddy.domain.chat

import android.util.Log
import d.tmesaric.divebuddy.presentation.chat.ChatViewModel
import okhttp3.Response
import okhttp3.WebSocket

class WebSocketListener(
    private val viewModel: ChatViewModel
): okhttp3.WebSocketListener() {

    private val TAG = "WebSocket"
    // on open we send Android device Connected
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        viewModel.setStatus(true)
        webSocket.send("Android Device Connected")
        Log.d(TAG, "onOpen: Connection opened")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        //viewModel.addMessage(text)
        Log.d(TAG, "receivedMessage: $text")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        Log.d(TAG, "onClosing: $code $reason")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosed(webSocket, code, reason)
        viewModel.setStatus(false)
        //viewModel.closeConnection()
        Log.d(TAG, "onClosed: $code $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.d(TAG, "onFailure: ${t.message} $response")
        super.onFailure(webSocket, t, response)
    }
}