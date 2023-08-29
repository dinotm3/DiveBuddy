package d.tmesaric.divebuddy.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _socketStatus = MutableStateFlow(false)
    val socketStatus: StateFlow<Boolean> = _socketStatus

    private val _messages = MutableStateFlow<List<Pair<Boolean, String>>>(emptyList())
    val messages: StateFlow<List<Pair<Boolean, String>>> = _messages

    fun addMessage(message: Pair<Boolean, String>) {
        viewModelScope.launch(Dispatchers.Main) {
            _messages.value = _messages.value + message
        }
    }

    fun setStatus(status: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        _socketStatus.value = status
    }
}