package d.tmesaric.divebuddy.domain.chat

data class ChatMessage(
    val isSentByUser: Boolean,
    val content: String,
    val timestamp: Long
)
