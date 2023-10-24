package d.tmesaric.divebuddy.presentation.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatMessageItem(isSentByUser: Boolean, content: String) {
    val lightBlueishColor = Color(0x8037BFFF)
    val backgroundColor = if (isSentByUser) lightBlueishColor else Color(0xFFFFFFFF)
    val borderColor = if (isSentByUser) Color(0xFF4CAF50) else Color.Gray
    val textColor = if (isSentByUser) Color.Black else Color.Black
    val alignment = if (isSentByUser) Alignment.CenterEnd else Alignment.CenterStart

    val shape = if (isSentByUser) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 0.dp, bottomStart = 16.dp)
    } else {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 0.dp)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(backgroundColor, shape = shape)
            .border(1.dp, borderColor, shape = shape)
            .padding(8.dp),
    ) {
        Text(
            text = content,
            color = textColor,
            fontSize = 16.sp,
            modifier = Modifier
                .align(alignment)
                .padding(horizontal = 16.dp)
        )
    }
}


