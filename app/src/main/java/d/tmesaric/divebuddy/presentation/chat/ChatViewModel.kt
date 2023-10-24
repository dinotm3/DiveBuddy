package d.tmesaric.divebuddy.presentation.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import d.tmesaric.divebuddy.domain.chat.ChatMessage
import d.tmesaric.divebuddy.domain.chat.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatViewModel() : ViewModel()  {

    private val chatRepository = ChatRepository()

    private val _socketStatus = MutableStateFlow(false)
    val socketStatus: StateFlow<Boolean> = _socketStatus

    private val _messages = MutableStateFlow<List<String>>(emptyList())
    val messages: StateFlow<List<String>> = _messages

    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(emptyList())
    private val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages

    fun sendChatMessage(senderId: String, content: String) {
        viewModelScope.launch {
            chatRepository.sendChatMessage(senderId, content)
        }
    }

    fun receiveChatMessage(message: ChatMessage) {
        _chatMessages.value = chatMessages.value + message
    }

    fun addMessage(message: String) {
        viewModelScope.launch(Dispatchers.Main) {
            if(_socketStatus.value) {
                _messages.value = _messages.value + message
            }
        }
        Log.d("Test", "addMessage: $message")

    }


    fun setStatus(status: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        _socketStatus.value = status
        Log.d("Test", "socketStatus: " + _socketStatus.value)
    }
}