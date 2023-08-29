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
import okhttp3.WebSocket

@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val webSocketListener = remember { WebSocketListener(viewModel) }
    val okHttpClient = OkHttpClient()
    var webSocket: WebSocket? = null
    val coroutineScope = rememberCoroutineScope()
    val isConnected by viewModel.socketStatus.collectAsState(false)
    val messages by viewModel.messages.collectAsState(emptyList())
    val connectionStatusText = if (isConnected) "Connected" else "Disconnected"

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = connectionStatusText)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(messages) { message ->
                val (isUser, text) = message
                Text(
                    text = "${if (isUser) "You: " else "Other: "} $text",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

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
    }
}