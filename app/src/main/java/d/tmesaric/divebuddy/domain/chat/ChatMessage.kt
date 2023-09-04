package d.tmesaric.divebuddy.domain.chat

data class ChatMessage(
    val senderId: String,
    val content: String,
    val timestamp: Long
)
