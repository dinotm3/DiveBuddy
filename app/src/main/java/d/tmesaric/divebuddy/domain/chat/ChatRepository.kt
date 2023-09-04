package d.tmesaric.divebuddy.domain.chat

import androidx.hilt.navigation.HiltViewModelFactory
import androidx.lifecycle.ViewModel
import d.tmesaric.divebuddy.presentation.chat.ChatViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.WebSocket
import javax.inject.Inject

class ChatRepository() {

    // TODO: DELETE THIS AND USE STREAM SDK
    suspend fun sendChatMessage(senderId: String, content: String) {
        val chatMessage = ChatMessage(senderId, content, System.currentTimeMillis())
/*        val json = Json.encodeToString(chatMessage)
        webSocket.send(json)*/
    }

    // WebSocketListener should call this method to receive messages
/*    fun onMessageReceived(message: ChatMessage) {
        viewModel.receiveChatMessage(message)
    }*/
}
