package d.tmesaric.divebuddy.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import d.tmesaric.divebuddy.domain.chat.WebSocketListener
import okhttp3.OkHttpClient
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import d.tmesaric.divebuddy.domain.chat.ChatMessage
import d.tmesaric.divebuddy.ui.theme.DarkBlue
import okhttp3.WebSocket

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val webSocketListener = remember { WebSocketListener(viewModel) }
    val okHttpClient = OkHttpClient()
    var webSocket: WebSocket? = null
    val coroutineScope = rememberCoroutineScope()
    val isConnected by viewModel.socketStatus.collectAsState(false)

    /*Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val user1 = User(4, "Luka", "luka@test.com", "hrv", 0.0, 0.0, null, null)
        val user2 = User(5, "Borna", "borna@test.com", "hrv", 0.0, 0.0, null, null)

        Button(onClick = {
            // TODO move to init{} and remove this button - leave like this for now because of easier testing!
            coroutineScope.launch {
                val request = Request.Builder()
                    .url("$BASE_URL_WEB_SOCKET/${user1.id}/${user2.id}")
                    .build()
                webSocket = okHttpClient.newWebSocket(request, webSocketListener)
            }
        }) {
            Text(text = "Connect")
        }

        Button(onClick = {
            // TODO: use viewModel here for better abstraction - add close function
            webSocket?.close(1000, "Canceled by user")
        }) {
            Text(text = "Disconnect")
        }

        Button(onClick = {
            // TODO: same here like for disconnect
            webSocket?.send("Message from Android")
            //viewModel.sendChatMessage("user", "Message from Android")

        }) {
            Text(text = "Send message")
        }
    }*/


    // CHATGPT
    var text by remember { mutableStateOf("") }
    //var messages by remember { mutableStateOf(listOf<ChatMessage>()) }

    val messages = listOf(
        ChatMessage(
            isSentByUser = false,
            content = "Hello!",
            timestamp = System.currentTimeMillis()
        ),
        ChatMessage(
            isSentByUser = true,
            content = "Hi there!",
            timestamp = System.currentTimeMillis() + 1000
        ),
        ChatMessage(
            isSentByUser = false,
            content = "How are you?",
            timestamp = System.currentTimeMillis() + 2000
        ),
        ChatMessage(
            isSentByUser = true,
            content = "I'm doing well, thanks!",
            timestamp = System.currentTimeMillis() + 3000
        ),
        ChatMessage(
            isSentByUser = false,
            content = "That's great to hear!",
            timestamp = System.currentTimeMillis() + 4000
        ),
        ChatMessage(
            isSentByUser = true,
            content = "Yes, it is!",
            timestamp = System.currentTimeMillis() + 5000
        ),
        ChatMessage(
            isSentByUser = false,
            content = "Have a nice day!",
            timestamp = System.currentTimeMillis() + 6000
        ),
        ChatMessage(
            isSentByUser = true,
            content = "You too!",
            timestamp = System.currentTimeMillis() + 7000
        )
    )

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        // Chat Messages
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(messages.size) { index ->
                ChatMessageItem(
                    isSentByUser = messages[index].isSentByUser,
                    content = messages[index].content,
                    //timestamp = messages[index].timestamp,
                )
            }
        }

        // Message Input
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .border(1.dp, DarkBlue)
        ) {
            BasicTextField(
                value = text,
                onValueChange = { newText ->
                    text = newText
                },
                textStyle = TextStyle(fontSize = 16.sp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = {
/*                        if (text.isNotBlank()) {
                            messages = messages + Message(text, true)
                            text = ""
                            keyboardController?.hide()
                        }*/
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(8.dp)
            )

            Button(
                onClick = {
/*                    if (text.isNotBlank()) {
                        messages = messages + Message(text, true)
                        text = ""
                    }*/
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = "Send")
            }
        }
    }
}
