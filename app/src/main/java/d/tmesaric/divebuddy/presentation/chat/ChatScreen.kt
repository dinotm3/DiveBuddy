package d.tmesaric.divebuddy.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import d.tmesaric.divebuddy.presentation.chat.ChatViewModel
import d.tmesaric.divebuddy.domain.chat.WebSocketListener
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import androidx.compose.runtime.*
import d.tmesaric.divebuddy.common.Constants.BASE_URL_WEB_SOCKET
import d.tmesaric.divebuddy.presentation.finder.FinderListItem
import kotlinx.coroutines.flow.toList
import okhttp3.WebSocket

@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val webSocketListener = remember { WebSocketListener(viewModel) }
    val okHttpClient = OkHttpClient()
    var webSocket: WebSocket? = null
    val coroutineScope = rememberCoroutineScope()
    val isConnected by viewModel.socketStatus.collectAsState(false)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Button(onClick = {
            coroutineScope.launch {
                val request = Request.Builder()
                    .url(BASE_URL_WEB_SOCKET)
                    .build()
                webSocket = okHttpClient.newWebSocket(request, webSocketListener)
            }
        }) {
            Text(text = "Connect")
        }

        Button(onClick = {
            webSocket?.close(1000, "Canceled by user")
        }) {
            Text(text = "Disconnect")
        }

        Button(onClick = {
            webSocket?.send("Message from Android")
            viewModel.sendChatMessage("user", "Message from Android")

        }) {
            Text(text = "Send message")
        }
    }
}